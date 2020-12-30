package cn.sp.spi.balance;

import cn.sp.annotation.LoadBalanceAno;
import cn.sp.constants.LoadBalanceConstants;
import cn.sp.pojo.dto.ServiceInstance;
import cn.sp.spi.LoadBalance;

import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/30
 */
@LoadBalanceAno(LoadBalanceConstants.WEIGHT_ROUND)
public class WeightRoundBalance implements LoadBalance {

    private volatile int index;

    @Override
    public synchronized ServiceInstance chooseOne(List<ServiceInstance> instances) {
        int allWeight = instances.stream().mapToInt(ServiceInstance::getWeight).sum();
        int number = (index++) % allWeight;
        for (ServiceInstance instance : instances) {
            if (instance.getWeight() > number) {
                return instance;
            }
            number -= instance.getWeight();
        }
        return null;
    }
}
