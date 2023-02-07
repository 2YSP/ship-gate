package cn.sp.sync;

import cn.sp.cache.PluginCache;
import cn.sp.cache.RouteRuleCache;
import cn.sp.cache.ServiceCache;
import cn.sp.config.ServerConfigProperties;
import cn.sp.constants.NacosConstants;
import cn.sp.constants.ShipExceptionEnum;
import cn.sp.constants.ShipPluginEnum;
import cn.sp.exception.ShipException;
import cn.sp.pojo.dto.AppRuleDTO;
import cn.sp.pojo.dto.ServiceInstance;
import cn.sp.utils.GsonUtils;
import cn.sp.utils.IpUtil;
import cn.sp.utils.ShipThreadFactory;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @Author: Ship
 * @Description: sync data to local cache
 * @Date: Created in 2020/12/25
 */
@Configuration
public class DataSyncTaskListener implements ApplicationListener<ContextRefreshedEvent> {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataSyncTaskListener.class);

    private static ScheduledThreadPoolExecutor scheduledPool = new ScheduledThreadPoolExecutor(1,
            new ShipThreadFactory("service-sync", true).create());

    @NacosInjected
    private NamingService namingService;

    @Autowired
    private ServerConfigProperties properties;

    private static ConfigService configService;

    private Environment environment;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() != null) {
            return;
        }
        environment = event.getApplicationContext().getEnvironment();
        scheduledPool.scheduleWithFixedDelay(new DataSyncTask(namingService)
                , 0L, properties.getCacheRefreshInterval(), TimeUnit.SECONDS);
        registItself();
        initConfig();
    }

    private void registItself() {
        Instance instance = new Instance();
        instance.setIp(IpUtil.getLocalIpAddress());
        instance.setPort(Integer.valueOf(environment.getProperty("server.port")));
        try {
            namingService.registerInstance("ship-server", NacosConstants.APP_GROUP_NAME, instance);
        } catch (NacosException e) {
            throw new ShipException(ShipExceptionEnum.CONNECT_NACOS_ERROR);
        }
    }

    private void initConfig() {
        try {
            String serverAddr = environment.getProperty("nacos.discovery.server-addr");
            Assert.hasText(serverAddr, "nacos server addr is missing");
            configService = NacosFactory.createConfigService(serverAddr);
            // pull config in first time
            String config = configService.getConfig(NacosConstants.DATA_ID_NAME, NacosConstants.APP_GROUP_NAME, 5000);
            DataSyncTaskListener.updateConfig(config);
            // add config listener
            configService.addListener(NacosConstants.DATA_ID_NAME, NacosConstants.APP_GROUP_NAME, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    LOGGER.info("receive config info:\n{}", configInfo);
                    DataSyncTaskListener.updateConfig(configInfo);
                }
            });
        } catch (NacosException e) {
            throw new ShipException(ShipExceptionEnum.CONNECT_NACOS_ERROR);
        }
    }


    public static void updateConfig(String configInfo) {
        List<AppRuleDTO> list = GsonUtils.fromJson(configInfo, new TypeToken<List<AppRuleDTO>>() {
        }.getType());
        Map<String, List<AppRuleDTO>> map = list.stream().collect(Collectors.groupingBy(AppRuleDTO::getAppName));
        RouteRuleCache.add(map);
        LOGGER.info("update route rule cache success");
    }

}
