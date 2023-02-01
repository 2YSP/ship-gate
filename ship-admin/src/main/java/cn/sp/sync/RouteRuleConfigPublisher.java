package cn.sp.sync;

import cn.sp.constants.NacosConstants;
import cn.sp.pojo.dto.AppRuleDTO;
import cn.sp.service.RuleService;
import cn.sp.utils.GsonUtils;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2023/2/1
 */
@Component
public class RouteRuleConfigPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteRuleConfigPublisher.class);

    @Resource
    private RuleService ruleService;

    @Value("${nacos.discovery.server-addr}")
    private String baseUrl;


    /**
     * publish service route rule config to Nacos
     */
    public void publishRouteRuleConfig() {
        List<AppRuleDTO> ruleDTOS = ruleService.getEnabledRule();
        // publish config
        String serverAddr = baseUrl;
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        try {
            ConfigService configService = NacosFactory.createConfigService(properties);
            String content = GsonUtils.toJson(ruleDTOS);
            boolean success = configService.publishConfig(NacosConstants.DATA_ID_NAME, NacosConstants.APP_GROUP_NAME, content);
            if (success) {
                LOGGER.info("publish service route rule config success!");
            } else {
                LOGGER.error("publish service route rule config fail!");
            }
        } catch (NacosException e) {
            LOGGER.error("read time out or net error", e);
        }
    }
}
