package cn.sp.cache;

import cn.sp.exception.ShipException;
import cn.sp.pojo.dto.AppRuleDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/27
 */
public class RouteRuleCache {
    /**
     * route rule config cache map
     * key: appName
     */
    private static final Map<String, List<AppRuleDTO>> ROUTE_RULE_MAP = new ConcurrentHashMap<>();

    public static void add(Map<String, List<AppRuleDTO>> map) {
        ROUTE_RULE_MAP.putAll(map);
    }

    public static void remove(Map<String, List<AppRuleDTO>> map) {
        // todo
    }


    /**
     * get rules by appName
     *
     * @param appName
     * @return
     */
    public static List<AppRuleDTO> getRules(String appName) {
        return Optional.ofNullable(ROUTE_RULE_MAP.get(appName))
                .orElseThrow(() -> new ShipException("please config route rule in ship-admin first!"));
    }

}
