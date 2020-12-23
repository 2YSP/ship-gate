package cn.sp;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by 2YSP on 2020/12/21
 */
@Configuration
@Import(value = {AutoRegisterListener.class})
@EnableConfigurationProperties(value = {ClientConfigProperties.class})
public class ClientAutoConfigure {



}
