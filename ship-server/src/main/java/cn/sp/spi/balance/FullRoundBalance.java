package cn.sp.spi.balance;

import cn.sp.annotation.LoadBalanceAno;
import cn.sp.constants.LoadBalanceConstants;
import cn.sp.pojo.dto.ServiceInstance;
import cn.sp.spi.LoadBalance;

import java.util.List;

/**
 * 轮询算法
 * Created by 2YSP on 2020/12/27
 */
@LoadBalanceAno(LoadBalanceConstants.ROUND)
public class FullRoundBalance implements LoadBalance {

    private volatile int index;

    @Override
    public ServiceInstance chooseOne(List<ServiceInstance> instances) {
        synchronized (this) {
            if (index == instances.size()) {
                index = 0;
            }
            return instances.get(index++);
        }
    }
}
