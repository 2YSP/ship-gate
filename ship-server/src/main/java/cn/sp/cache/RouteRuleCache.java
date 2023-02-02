package cn.sp.cache;

import cn.sp.exception.ShipException;
import cn.sp.pojo.dto.AppRuleDTO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
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
    private static final Map<String, CopyOnWriteArrayList<AppRuleDTO>> ROUTE_RULE_MAP = new ConcurrentHashMap<>();

    /**
     * add new rules to cache map and remove expired
     *
     * @param map
     */
    public static synchronized void add(Map<String, List<AppRuleDTO>> map) {
        map.forEach((key, value) -> {
            ROUTE_RULE_MAP.put(key, new CopyOnWriteArrayList(value));
        });
        List<String> expiredKeys = ROUTE_RULE_MAP.keySet().stream().filter(i -> !map.containsKey(i)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(expiredKeys)) {
            return;
        }
        expiredKeys.forEach(k -> ROUTE_RULE_MAP.remove(k));
    }

//    /**
//     * remove rule from cache map
//     *
//     * @param map
//     */
//    public static void remove(Map<String, List<AppRuleDTO>> map) {
//        for (Map.Entry<String, List<AppRuleDTO>> entry : map.entrySet()) {
//            String appName = entry.getKey();
//            List<Integer> ruleIds = entry.getValue().stream().map(AppRuleDTO::getId).collect(Collectors.toList());
//            CopyOnWriteArrayList<AppRuleDTO> ruleDTOS = ROUTE_RULE_MAP.getOrDefault(appName, new CopyOnWriteArrayList<>());
//            ruleDTOS.removeIf(r -> ruleIds.contains(r.getId()));
//            if (CollectionUtils.isEmpty(ruleDTOS)) {
//                // remove all
//                ROUTE_RULE_MAP.remove(appName);
//            } else {
//                // remove some of them
//                ROUTE_RULE_MAP.put(appName, ruleDTOS);
//            }
//        }
//    }


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
