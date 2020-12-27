package cn.sp.filter;

import cn.sp.annotation.LoadBalanceAno;
import cn.sp.chain.PluginChain;
import cn.sp.config.ServerConfigProperties;
import cn.sp.plugin.DynamicRoutePlugin;
import cn.sp.plugin.TestPlugin;
import cn.sp.spi.LoadBalance;
import cn.sp.spi.balance.FullRoundBalance;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.ServiceLoader;

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
        PluginChain pluginChain = new PluginChain(properties);
        // todo get enabled plugin by app config
        pluginChain.addPlugin(new DynamicRoutePlugin(properties));
//        pluginChain.addPlugin(new TestPlugin());
        return pluginChain.execute(exchange, pluginChain);
    }


}
