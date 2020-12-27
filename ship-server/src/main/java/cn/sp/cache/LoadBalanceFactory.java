package cn.sp.cache;

import cn.sp.annotation.LoadBalanceAno;
import cn.sp.exception.ShipException;
import cn.sp.spi.LoadBalance;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/27
 */
public final class LoadBalanceFactory {

    /**
     * key: appName:version
     */
    private static final Map<String, LoadBalance> LOAD_BALANCE_MAP = new ConcurrentHashMap<>();

    private LoadBalanceFactory(){

    }

    /**
     * get LoadBalance instance
     * @param name
     * @param appName
     * @param version
     * @return
     */
    public static LoadBalance getInstance(final String name, String appName, String version) {
        String key = appName + ":" + version;
        return LOAD_BALANCE_MAP.computeIfAbsent(key, (k) -> getLoadBalance(name));
    }

    /**
     * use spi to match load balance algorithm by server config
     *
     * @param name
     * @return
     */
    private static LoadBalance getLoadBalance(String name) {
        ServiceLoader<LoadBalance> loader = ServiceLoader.load(LoadBalance.class);
        Iterator<LoadBalance> iterator = loader.iterator();
        while (iterator.hasNext()) {
            LoadBalance loadBalance = iterator.next();
            LoadBalanceAno ano = loadBalance.getClass().getAnnotation(LoadBalanceAno.class);
            Assert.notNull(ano, "load balance name can not be empty!");
            if (name.equals(ano.value())) {
                return loadBalance;
            }
        }
        throw new ShipException("invalid load balance config");
    }
}
