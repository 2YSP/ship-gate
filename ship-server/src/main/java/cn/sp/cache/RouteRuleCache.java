package cn.sp.cache;

import cn.sp.exception.ShipException;
import cn.sp.pojo.dto.AppRuleDTO;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    /**
     * add rule to cache map
     *
     * @param map
     */
    public static void add(Map<String, List<AppRuleDTO>> map) {
        ROUTE_RULE_MAP.putAll(map);
    }

    /**
     * remove rule from cache map
     *
     * @param map
     */
    public static void remove(Map<String, List<AppRuleDTO>> map) {
        for (Map.Entry<String, List<AppRuleDTO>> entry : map.entrySet()) {
            String appName = entry.getKey();
            List<Integer> ruleIds = entry.getValue().stream().map(AppRuleDTO::getId).collect(Collectors.toList());
            List<AppRuleDTO> ruleDTOS = ROUTE_RULE_MAP.getOrDefault(appName, new ArrayList<>());
            List<AppRuleDTO> leftRules = ruleDTOS.stream().filter(r -> !ruleIds.contains(r.getId())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(leftRules)) {
                // remove all
                ROUTE_RULE_MAP.remove(appName);
            } else {
                // remove some of them
                ROUTE_RULE_MAP.put(appName, leftRules);
            }
        }
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
