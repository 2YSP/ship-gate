package cn.sp.plugin;

import cn.sp.chain.PluginChain;
import cn.sp.constants.ShipPluginEnum;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/25
 */
public class DynamicRoutePlugin implements ShipPlugin {

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
        System.out.println("I am dynamic route");
        return pluginChain.execute(exchange,pluginChain);
    }
}
