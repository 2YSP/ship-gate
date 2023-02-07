package cn.sp.sync;

import cn.sp.cache.PluginCache;
import cn.sp.cache.ServiceCache;
import cn.sp.constants.NacosConstants;
import cn.sp.constants.ShipPluginEnum;
import cn.sp.pojo.dto.ServiceInstance;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2023/2/7
 */
public class DataSyncTask implements Runnable {

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
