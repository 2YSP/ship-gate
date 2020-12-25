package cn.sp.chain;

import cn.sp.plugin.DynamicRoutePlugin;
import cn.sp.plugin.ShipPlugin;
import cn.sp.plugin.TestPlugin;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/25
 */
public class PluginChain implements ShipPlugin {
    /**
     * the pos point to current plugin
     */
    private int pos;
    /**
     * the plugins of chain
     */
    private List<ShipPlugin> plugins;

    public void addPlugin(ShipPlugin shipPlugin) {
        if (plugins == null) {
            plugins = new ArrayList<>();
        }
        plugins.add(shipPlugin);
        // order by the plugin's order
        plugins.sort(Comparator.comparing(ShipPlugin::order));
    }

    @Override
    public Integer order() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public Mono<Void> execute(ServerWebExchange exchange, PluginChain pluginChain) {
        if (pos == plugins.size()) {
            return exchange.getResponse().setComplete();
        }
        return pluginChain.plugins.get(pos++).execute(exchange, pluginChain);
    }
}
