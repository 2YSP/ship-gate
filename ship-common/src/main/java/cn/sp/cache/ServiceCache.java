package cn.sp.cache;

import cn.sp.pojo.dto.ServiceInstance;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/25
 */
public class ServiceCache {
    /**
     * key: serviceName
     */
    private static final Map<String, List<ServiceInstance>> SERVICE_MAP = new ConcurrentHashMap<>();

    /**
     * get all instances by serviceName
     *
     * @param serviceName
     * @return
     */
    public static List<ServiceInstance> getAllInstances(String serviceName) {
        return SERVICE_MAP.get(serviceName);
    }


    public static void add(String serviceName, List<ServiceInstance> list) {
        SERVICE_MAP.put(serviceName, list);
    }
}
