package cn.sp.event.listener;

import cn.sp.event.RuleAddEvent;
import cn.sp.event.RuleDeleteEvent;
import cn.sp.sync.RouteRuleConfigPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/29
 */
@Component
public class RuleEventListener {

    @Resource
    private RouteRuleConfigPublisher configPublisher;

    @EventListener
    public void onAdd(RuleAddEvent ruleAddEvent) {
        configPublisher.publishRouteRuleConfig();
    }

    @EventListener
    public void onDelete(RuleDeleteEvent ruleDeleteEvent) {
        configPublisher.publishRouteRuleConfig();
    }
}
