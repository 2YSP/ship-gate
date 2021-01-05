package cn.sp.sync;

import cn.sp.cache.PluginCache;
import cn.sp.cache.ServiceCache;
import cn.sp.config.ServerConfigProperties;
import cn.sp.constants.NacosConstants;
import cn.sp.constants.ShipPluginEnum;
import cn.sp.pojo.dto.ServiceInstance;
import cn.sp.utils.ShipThreadFactory;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.CollectionUtils;

import java.util.*;
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

    private static ScheduledThreadPoolExecutor scheduledPool = new ScheduledThreadPoolExecutor(1,
            new ShipThreadFactory("service-sync", true).create());

    @NacosInjected
    private NamingService namingService;

    @Autowired
    private ServerConfigProperties properties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() != null) {
            return;
        }
        scheduledPool.scheduleWithFixedDelay(new DataSyncTask(namingService)
                , 0L, properties.getCacheRefreshInterval(), TimeUnit.SECONDS);
        WebsocketSyncCacheServer websocketSyncCacheServer = new WebsocketSyncCacheServer(properties.getWebSocketPort());
        websocketSyncCacheServer.start();
    }


    class DataSyncTask implements Runnable {

        private NamingService namingService;

        public DataSyncTask(NamingService namingService) {
            this.namingService = namingService;
        }

        @Override
        public void run() {
            try {
                // get all app names
                ListView<String> services = namingService.getServicesOfServer(1, Integer.MAX_VALUE, NacosConstants.APP_GROUP_NAME);
                if (CollectionUtils.isEmpty(services.getData())) {
                    return;
                }
                List<String> appNames = services.getData();
                List<String> onlineAppNames = new ArrayList<>();
                // get all instances
                for (String appName : appNames) {
                    List<Instance> instanceList = namingService.getAllInstances(appName, NacosConstants.APP_GROUP_NAME);
                    if (CollectionUtils.isEmpty(instanceList)) {
                        continue;
                    }
                    ServiceCache.add(appName, buildServiceInstances(instanceList));
                    List<String> pluginNames = getEnabledPlugins(instanceList);
                    PluginCache.add(appName, pluginNames);
                    onlineAppNames.add(appName);
                }
                ServiceCache.removeExpired(onlineAppNames);
                PluginCache.removeExpired(onlineAppNames);

            } catch (NacosException e) {
                e.printStackTrace();
            }
        }

        private List<String> getEnabledPlugins(List<Instance> instanceList) {
            Instance instance = instanceList.get(0);
            Map<String, String> metadata = instance.getMetadata();
            // plugins: DynamicRoute,Auth
            String plugins = metadata.getOrDefault("plugins", ShipPluginEnum.DYNAMIC_ROUTE.getName());
            return Arrays.stream(plugins.split(",")).collect(Collectors.toList());
        }

        private List<ServiceInstance> buildServiceInstances(List<Instance> instanceList) {
            List<ServiceInstance> list = new LinkedList<>();
            instanceList.forEach(instance -> {
                Map<String, String> metadata = instance.getMetadata();
                ServiceInstance serviceInstance = new ServiceInstance();
                serviceInstance.setAppName(metadata.get("appName"));
                serviceInstance.setIp(instance.getIp());
                serviceInstance.setPort(instance.getPort());
                serviceInstance.setVersion(metadata.get("version"));
                serviceInstance.setWeight((int) instance.getWeight());
                list.add(serviceInstance);
            });
            return list;
        }
    }
}
