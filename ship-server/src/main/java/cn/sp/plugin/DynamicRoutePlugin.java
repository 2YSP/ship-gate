package cn.sp.plugin;

import cn.sp.cache.LoadBalanceFactory;
import cn.sp.cache.RouteRuleCache;
import cn.sp.cache.ServiceCache;
import cn.sp.chain.PluginChain;
import cn.sp.chain.ShipResponseUtil;
import cn.sp.config.ServerConfigProperties;
import cn.sp.constants.MatchObjectEnum;
import cn.sp.constants.ShipExceptionEnum;
import cn.sp.constants.ShipPluginEnum;
import cn.sp.exception.ShipException;
import cn.sp.pojo.dto.ServiceInstance;
import cn.sp.pojo.dto.AppRuleDTO;
import cn.sp.spi.LoadBalance;
import cn.sp.utils.StringTools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/25
 */
public class DynamicRoutePlugin extends AbstractShipPlugin {

    private final static Logger LOGGER = LoggerFactory.getLogger(DynamicRoutePlugin.class);

    private WebClient webClient;

    private Gson gson = new GsonBuilder().create();

    public DynamicRoutePlugin(ServerConfigProperties properties) {
        super(properties);
        webClient = WebClient.create();
    }

    @Override
    public Integer order() {
        return ShipPluginEnum.DYNAMIC_ROUTE.getOrder();
    }

    @Override
    public String name() {
        return ShipPluginEnum.DYNAMIC_ROUTE.getName();
    }

    @Override
    public Mono<Void> execute(ServerWebExchange exchange, PluginChain pluginChain) {
        String appName = pluginChain.getAppName();
        ServiceInstance serviceInstance = chooseInstance(appName, exchange.getRequest());
        LOGGER.info("selected instance is [{}]", gson.toJson(serviceInstance));
        // request service
        String url = buildUrl(exchange, serviceInstance);
        return forward(exchange, url);
    }

    /**
     * forward request to backend service
     *
     * @param exchange
     * @param url
     * @return
     */
    private Mono<Void> forward(ServerWebExchange exchange, String url) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpMethod method = request.getMethod();

        WebClient.RequestBodySpec requestBodySpec = webClient.method(method).uri(url).headers((headers) -> {
            headers.addAll(request.getHeaders());
        });

        WebClient.RequestHeadersSpec<?> reqHeadersSpec;
        if (requireHttpBody(method)) {
            reqHeadersSpec = requestBodySpec.body(BodyInserters.fromDataBuffers(request.getBody()));
        } else {
            reqHeadersSpec = requestBodySpec;
        }
        // nio->callback->nio
        return reqHeadersSpec.exchange().timeout(Duration.ofMillis(properties.getTimeOutMillis()))
                .onErrorResume(ex -> {
                    return Mono.defer(() -> {
                        String errorResultJson = "";
                        if (ex instanceof TimeoutException) {
                            errorResultJson = "{\"code\":5001,\"message\":\"network timeout\"}";
                        } else {
                            errorResultJson = "{\"code\":5000,\"message\":\"system error\"}";
                        }
                        return ShipResponseUtil.doResponse(exchange, errorResultJson);
                    }).then(Mono.empty());
                }).flatMap(backendResponse -> {
                    response.setStatusCode(backendResponse.statusCode());
                    response.getHeaders().putAll(backendResponse.headers().asHttpHeaders());
                    return response.writeWith(backendResponse.bodyToFlux(DataBuffer.class));
                });
    }

    /**
     * weather the http method need http body
     *
     * @param method
     * @return
     */
    private boolean requireHttpBody(HttpMethod method) {
        if (method.equals(HttpMethod.POST) || method.equals(HttpMethod.PUT) || method.equals(HttpMethod.PATCH)) {
            return true;
        }
        return false;
    }

    private String buildUrl(ServerWebExchange exchange, ServiceInstance serviceInstance) {
        ServerHttpRequest request = exchange.getRequest();
        String query = request.getURI().getQuery();
        String path = request.getPath().value().replaceFirst("/" + serviceInstance.getAppName(), "");
        String url = "http://" + serviceInstance.getIp() + ":" + serviceInstance.getPort() + path;
        if (!StringUtils.isEmpty(query)) {
            url = url + "?" + query;
        }
        return url;
    }



    /**
     * choose an ServiceInstance according to route rule config and load balancing algorithm
     *
     * @param appName
     * @param request
     * @return
     */
    private ServiceInstance chooseInstance(String appName, ServerHttpRequest request) {
        List<ServiceInstance> serviceInstances = ServiceCache.getAllInstances(appName);
        if (CollectionUtils.isEmpty(serviceInstances)) {
            LOGGER.error("service instance of {} not find", appName);
            throw new ShipException(ShipExceptionEnum.SERVICE_NOT_FIND);
        }
        String version = matchAppVersion(appName, request);
        if (StringUtils.isEmpty(version)) {
            throw new ShipException("match app version error");
        }
        // filter serviceInstances by version
        List<ServiceInstance> instances = serviceInstances.stream().filter(i -> i.getVersion().equals(version)).collect(Collectors.toList());
        //Select an instance based on the load balancing algorithm
        LoadBalance loadBalance = LoadBalanceFactory.getInstance(properties.getLoadBalance(), appName, version);
        ServiceInstance serviceInstance = loadBalance.chooseOne(instances);
        return serviceInstance;
    }


    private String matchAppVersion(String appName, ServerHttpRequest request) {
        List<AppRuleDTO> rules = RouteRuleCache.getRules(appName);
        rules.sort(Comparator.comparing(AppRuleDTO::getPriority).reversed());
        for (AppRuleDTO rule : rules) {
            if (match(rule, request)) {
                return rule.getVersion();
            }
        }
        return null;
    }


    private boolean match(AppRuleDTO rule, ServerHttpRequest request) {
        String matchObject = rule.getMatchObject();
        String matchKey = rule.getMatchKey();
        String matchRule = rule.getMatchRule();
        Byte matchMethod = rule.getMatchMethod();
        if (MatchObjectEnum.DEFAULT.getCode().equals(matchObject)) {
            return true;
        } else if (MatchObjectEnum.QUERY.getCode().equals(matchObject)) {
            String param = request.getQueryParams().getFirst(matchKey);
            if (!StringUtils.isEmpty(param)) {
                return StringTools.match(param, matchMethod, matchRule);
            }
        } else if (MatchObjectEnum.HEADER.getCode().equals(matchObject)) {
            HttpHeaders headers = request.getHeaders();
            String headerValue = headers.getFirst(matchKey);
            if (!StringUtils.isEmpty(headerValue)) {
                return StringTools.match(headerValue, matchMethod, matchRule);
            }
        }
        return false;
    }

}
