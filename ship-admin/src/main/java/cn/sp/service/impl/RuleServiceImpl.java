package cn.sp.service.impl;

import cn.sp.bean.App;
import cn.sp.bean.RouteRule;
import cn.sp.constants.EnabledEnum;
import cn.sp.constants.ShipExceptionEnum;
import cn.sp.event.RuleAddEvent;
import cn.sp.event.RuleDeleteEvent;
import cn.sp.exception.ShipException;
import cn.sp.mapper.AppMapper;
import cn.sp.mapper.RouteRuleMapper;
import cn.sp.pojo.dto.AppRuleDTO;
import cn.sp.service.RuleService;
import cn.sp.transfer.AppRuleVOTransfer;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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

    @Resource
    private ApplicationEventPublisher eventPublisher;

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

    @Override
    public void add(AppRuleDTO appRuleDTO) {
        RouteRule routeRule = new RouteRule();
        BeanUtils.copyProperties(appRuleDTO, routeRule);
        routeRule.setEnabled(EnabledEnum.ENABLE.getCode());
        routeRule.setCreatedTime(LocalDateTime.now());
        ruleMapper.insert(routeRule);
        appRuleDTO.setId(routeRule.getId());
        eventPublisher.publishEvent(new RuleAddEvent(this, appRuleDTO));
    }

    @Override
    public void delete(Integer id) {
        RouteRule routeRule = ruleMapper.selectById(id);
        if (routeRule == null){
            throw new ShipException(ShipExceptionEnum.PARAM_ERROR);
        }
        AppRuleDTO appRuleDTO = new AppRuleDTO();
        BeanUtils.copyProperties(routeRule, appRuleDTO);
        App app = appMapper.selectById(appRuleDTO.getAppId());
        appRuleDTO.setAppName(app.getAppName());

        ruleMapper.deleteById(id);
        eventPublisher.publishEvent(new RuleDeleteEvent(this, appRuleDTO));
    }
}
