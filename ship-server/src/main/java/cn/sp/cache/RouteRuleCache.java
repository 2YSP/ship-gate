package cn.sp.cache;

import cn.sp.constants.MatchMethodEnum;
import cn.sp.constants.MatchObjectEnum;
import cn.sp.exception.ShipException;
import cn.sp.pojo.vo.AppRuleVO;

import java.util.ArrayList;
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
    private static final Map<String, List<AppRuleVO>> ROUTE_RULE_MAP = new ConcurrentHashMap<>();

    static {
        List<AppRuleVO> list = new ArrayList<>();
        AppRuleVO ruleVO = new AppRuleVO();
        ruleVO.setAppName("order");
        ruleVO.setVersion("gray_1.0");
        ruleVO.setPriority(50);
        ruleVO.setMatchObject(MatchObjectEnum.HEADER.getCode());
        ruleVO.setMatchKey("name");
        ruleVO.setMatchMethod(MatchMethodEnum.EQUAL.getCode());
        ruleVO.setMatchRule("ship");
        list.add(ruleVO);

        AppRuleVO ruleVO2 = new AppRuleVO();
        ruleVO2.setAppName("order");
        ruleVO2.setVersion("prod_1.0");
        ruleVO2.setPriority(1);
        ruleVO2.setMatchObject(MatchObjectEnum.DEFAULT.getCode());
//        ruleVO2.setMatchObject(MatchObjectEnum.HEADER.getCode());
//        ruleVO2.setMatchKey("name");
//        ruleVO2.setMatchMethod(MatchMethodEnum.EQUAL.getCode());
//        ruleVO2.setMatchRule("ship");
        list.add(ruleVO2);
        ROUTE_RULE_MAP.put("order",list);
    }

    /**
     * get rules by appName
     * @param appName
     * @return
     */
    public static List<AppRuleVO> getRules(String appName) {
        return Optional.ofNullable(ROUTE_RULE_MAP.get(appName))
                .orElseThrow(() -> new ShipException("please config route rule in ship-admin first!"));
    }

}
