package cn.sp.sync;

import cn.sp.cache.ServiceCache;
import cn.sp.constants.NacosConstants;
import cn.sp.pojo.dto.ServiceInstance;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @Author: Ship
 * @Description: sync data to local cache
 * @Date: Created in 2020/12/25
 */
@Configuration
public class DataSyncTaskListener implements ApplicationListener<ContextRefreshedEvent> {

    private static ScheduledThreadPoolExecutor scheduledPool = new ScheduledThreadPoolExecutor(1,
            new ThreadFactoryBuilder().setNameFormat("data-sync-%d").build());

    @NacosInjected
    private NamingService namingService;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() != null) {
            return;
        }
        scheduledPool.scheduleWithFixedDelay(new DataSyncTask(namingService)
                , 0L, 10L, TimeUnit.SECONDS);
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
                // get all instances
                for (String appName : appNames) {
                    List<Instance> instanceList = namingService.getAllInstances(appName, NacosConstants.APP_GROUP_NAME);
                    if (CollectionUtils.isEmpty(instanceList)) {
                        continue;
                    }
                    ServiceCache.add(appName, buildServiceInstances(instanceList));
                }
                ServiceCache.removeExpired(appNames);
            } catch (NacosException e) {
                e.printStackTrace();
            }
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
                list.add(serviceInstance);
            });
            return list;
        }
    }
}
