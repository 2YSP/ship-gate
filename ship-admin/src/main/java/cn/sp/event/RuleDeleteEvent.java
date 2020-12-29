package cn.sp.event;

import cn.sp.pojo.dto.AppRuleDTO;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/29
 */
public class RuleDeleteEvent extends ApplicationEvent {

    private AppRuleDTO appRuleDTO;

    public RuleDeleteEvent(Object source, AppRuleDTO appRuleDTO) {
        super(source);
        this.appRuleDTO = appRuleDTO;
    }

    public AppRuleDTO getAppRuleDTO() {
        return appRuleDTO;
    }
}
