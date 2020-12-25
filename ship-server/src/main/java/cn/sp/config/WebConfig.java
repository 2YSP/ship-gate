package cn.sp.config;

import cn.sp.filter.PluginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/25
 */
@Configuration
@EnableWebFlux
public class WebConfig {

    @Bean
    public PluginFilter pluginFilter() {
        return new PluginFilter();
    }
}
