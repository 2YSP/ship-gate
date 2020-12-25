package cn.sp.plugin;


import cn.sp.chain.PluginChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/25
 */
public interface ShipPlugin {
    /**
     * lower values have higher priority
     *
     * @return
     */
    Integer order();

    /**
     * return current plugin name
     *
     * @return
     */
    String name();

    Mono<Void> execute(ServerWebExchange exchange,PluginChain pluginChain);

}
