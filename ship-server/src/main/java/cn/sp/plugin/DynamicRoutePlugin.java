package cn.sp.plugin;

import cn.sp.cache.LoadBalanceFactory;
import cn.sp.cache.ServiceCache;
import cn.sp.chain.PluginChain;
import cn.sp.chain.ShipResponseUtil;
import cn.sp.config.ServerConfigProperties;
import cn.sp.constants.ShipExceptionEnum;
import cn.sp.constants.ShipPluginEnum;
import cn.sp.exception.ShipException;
import cn.sp.pojo.dto.ServiceInstance;
import cn.sp.spi.LoadBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.TimeoutException;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/25
 */
public class DynamicRoutePlugin extends AbstractShipPlugin {

    private final static Logger LOGGER = LoggerFactory.getLogger(DynamicRoutePlugin.class);

    private WebClient webClient;

    private ServerConfigProperties configProperties;

    public DynamicRoutePlugin(ServerConfigProperties properties) {
        super(properties);
        webClient = WebClient.create();
        this.configProperties = properties;
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
        String appName = parseAppName(exchange);
        if (CollectionUtils.isEmpty(ServiceCache.getAllInstances(appName))) {
            throw new ShipException(ShipExceptionEnum.SERVICE_NOT_FIND);
        }
        ServiceInstance serviceInstance = choseInstance(appName);
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
        return reqHeadersSpec.exchange().timeout(Duration.ofMillis(3000))
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
        String path = exchange.getRequest().getPath().value().
                replaceFirst("/" + serviceInstance.getAppName(), "");
        String url = "http://" + serviceInstance.getIp() + ":" + serviceInstance.getPort() + path;
        return url;
    }

    private String parseAppName(ServerWebExchange exchange) {
        RequestPath path = exchange.getRequest().getPath();
        String appName = path.value().split("/")[1];
        return appName;
    }


    private ServiceInstance choseInstance(String appName) {
        List<ServiceInstance> serviceInstances = ServiceCache.getAllInstances(appName);
        if (CollectionUtils.isEmpty(serviceInstances)) {
            LOGGER.error("service instance of {} not find", appName);
            throw new ShipException(ShipExceptionEnum.SERVICE_NOT_FIND);
        }
        // todo chose service version by route rule
        //Select an instance based on the load balancing algorithm
        LoadBalance loadBalance = LoadBalanceFactory.getInstance(configProperties.getLoadBalance(), appName, "dev_1.0");
        ServiceInstance serviceInstance = loadBalance.chooseOne(serviceInstances);
        return serviceInstance;
    }



}
