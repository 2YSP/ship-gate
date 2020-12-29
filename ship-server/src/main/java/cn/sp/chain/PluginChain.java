package cn.sp.chain;

import cn.sp.cache.PluginCache;
import cn.sp.config.ServerConfigProperties;
import cn.sp.plugin.AbstractShipPlugin;
import cn.sp.plugin.ShipPlugin;
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
public class PluginChain extends AbstractShipPlugin {
    /**
     * the pos point to current plugin
     */
    private int pos;
    /**
     * the plugins of chain
     */
    private List<ShipPlugin> plugins;

    private final String appName;

    public PluginChain(ServerConfigProperties properties, String appName) {
        super(properties);
        this.appName = appName;
    }

    /**
     * add enabled plugin to chain
     *
     * @param shipPlugin
     */
    public void addPlugin(ShipPlugin shipPlugin) {
        if (plugins == null) {
            plugins = new ArrayList<>();
        }
        if (!PluginCache.isEnabled(appName, shipPlugin.name())) {
            return;
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

    public String getAppName() {
        return appName;
    }

}
