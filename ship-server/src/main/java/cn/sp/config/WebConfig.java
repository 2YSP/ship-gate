package cn.sp.config;

import cn.sp.filter.PluginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/25
 */
@Configuration
@EnableWebFlux
@EnableConfigurationProperties(ServerConfigProperties.class)
public class WebConfig {

    @Bean
    public PluginFilter pluginFilter(@Autowired ServerConfigProperties properties) {
        return new PluginFilter(properties);
    }

    /**
     * set order -2 to before DefaultErrorWebExceptionHandler(-1) ResponseStatusExceptionHandler(0)
     * @return
     */
    @Order(-2)
    @Bean
    public ShipExceptionHandler shipExceptionHandler(){
        return new ShipExceptionHandler();
    }

}
