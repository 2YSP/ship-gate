package cn.sp.filter;

import cn.sp.cache.ServiceCache;
import cn.sp.chain.PluginChain;
import cn.sp.config.ServerConfigProperties;
import cn.sp.constants.ShipExceptionEnum;
import cn.sp.exception.ShipException;
import cn.sp.plugin.AuthPlugin;
import cn.sp.plugin.DynamicRoutePlugin;
import org.springframework.http.server.RequestPath;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/25
 */
public class PluginFilter implements WebFilter {

    private ServerConfigProperties properties;

    public PluginFilter(ServerConfigProperties properties) {
        this.properties = properties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String appName = parseAppName(exchange);
        if (CollectionUtils.isEmpty(ServiceCache.getAllInstances(appName))) {
            throw new ShipException(ShipExceptionEnum.SERVICE_NOT_FIND);
        }
        PluginChain pluginChain = new PluginChain(properties, appName);
        // todo get enabled plugin by app config
        pluginChain.addPlugin(new DynamicRoutePlugin(properties));
        pluginChain.addPlugin(new AuthPlugin(properties));
        return pluginChain.execute(exchange, pluginChain);
    }

    private String parseAppName(ServerWebExchange exchange) {
        RequestPath path = exchange.getRequest().getPath();
        String appName = path.value().split("/")[1];
        return appName;
    }
}
