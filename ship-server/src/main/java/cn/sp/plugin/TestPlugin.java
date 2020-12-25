package cn.sp.plugin;

import cn.sp.chain.PluginChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/25
 */
public class TestPlugin implements ShipPlugin {


    @Override
    public Integer order() {
        return 2;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public Mono<Void> execute(ServerWebExchange exchange, PluginChain pluginChain) {
        System.out.println("I am test");
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap("hello world".getBytes())));
//        return pluginChain.execute(exchange, pluginChain);
    }


}
