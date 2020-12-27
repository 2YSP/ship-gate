package cn.sp.spi.balance;

import cn.sp.annotation.LoadBalanceAno;
import cn.sp.constants.LoadBalanceConstants;
import cn.sp.pojo.dto.ServiceInstance;
import cn.sp.spi.LoadBalance;

import java.util.List;
import java.util.Random;

/**
 * 随机算法
 */
@LoadBalanceAno(LoadBalanceConstants.RANDOM)
public class RandomBalance implements LoadBalance {

    private static Random random = new Random();

    @Override
    public ServiceInstance chooseOne(List<ServiceInstance> instances) {
        return instances.get(random.nextInt(instances.size()));
    }
}
