package cn.sp.spi;

import cn.sp.pojo.dto.ServiceInstance;

import java.util.List;

/**
 * Created by 2YSP on 2020/12/27
 */
public interface LoadBalance {
    /**
     * Select an instance based on the load balancing algorithm
     * @param instances
     * @return
     */
    ServiceInstance chooseOne(List<ServiceInstance> instances);
}
