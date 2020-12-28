package cn.sp.service.impl;

import cn.sp.bean.App;
import cn.sp.bean.RouteRule;
import cn.sp.constants.EnabledEnum;
import cn.sp.mapper.AppMapper;
import cn.sp.mapper.RouteRuleMapper;
import cn.sp.pojo.dto.AppRuleDTO;
import cn.sp.service.RuleService;
import cn.sp.transfer.AppRuleVOTransfer;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/28
 */
@Service
public class RuleServiceImpl implements RuleService {

    @Resource
    private RouteRuleMapper ruleMapper;

    @Resource
    private AppMapper appMapper;

    @Override
    public List<AppRuleDTO> getEnabledRule() {
        QueryWrapper<App> wrapper = Wrappers.query();
        wrapper.lambda().eq(App::getEnabled, EnabledEnum.ENABLE.getCode());
        List<App> apps = appMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(apps)) {
            return new ArrayList<>();
        }
        List<Integer> appIds = apps.stream().map(App::getId).collect(Collectors.toList());
        Map<Integer, String> nameMap = apps.stream().collect(Collectors.toMap(App::getId, App::getAppName));
        QueryWrapper<RouteRule> query = Wrappers.query();
        query.lambda().in(RouteRule::getAppId, appIds)
                .eq(RouteRule::getEnabled, EnabledEnum.ENABLE.getCode());
        List<RouteRule> routeRules = ruleMapper.selectList(query);
        List<AppRuleDTO> appRuleDTOS = AppRuleVOTransfer.INSTANCE.mapToVOList(routeRules);
        appRuleDTOS.forEach(r -> r.setAppName(nameMap.get(r.getAppId())));
        return appRuleDTOS;
    }
}
