package cn.sp.filter;

import cn.sp.chain.PluginChain;
import cn.sp.plugin.DynamicRoutePlugin;
import cn.sp.plugin.TestPlugin;
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

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        PluginChain pluginChain = new PluginChain();
        // todo get enabled plugin by app config
        pluginChain.addPlugin(new DynamicRoutePlugin());
        pluginChain.addPlugin(new TestPlugin());
        return pluginChain.execute(exchange, pluginChain);
    }
}
