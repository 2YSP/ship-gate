package cn.sp.plugin.impl;

import cn.sp.chain.PluginChain;
import cn.sp.config.ServerConfigProperties;
import cn.sp.constants.ShipPluginEnum;
import cn.sp.plugin.AbstractShipPlugin;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/29
 */
public class AuthPlugin extends AbstractShipPlugin {

    public AuthPlugin(ServerConfigProperties properties) {
        super(properties);
    }

    @Override
    public Integer order() {
        return ShipPluginEnum.AUTH.getOrder();
    }

    @Override
    public String name() {
        return ShipPluginEnum.AUTH.getName();
    }

    @Override
    public Mono<Void> execute(ServerWebExchange exchange, PluginChain pluginChain) {
        System.out.println("auth plugin");
        return pluginChain.execute(exchange, pluginChain);
    }
}
