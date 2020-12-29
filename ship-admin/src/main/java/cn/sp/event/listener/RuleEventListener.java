package cn.sp.event.listener;

import cn.sp.constants.OperationTypeEnum;
import cn.sp.event.RuleAddEvent;
import cn.sp.event.RuleDeleteEvent;
import cn.sp.pojo.dto.AppRuleDTO;
import cn.sp.pojo.dto.RouteRuleOperationDTO;
import cn.sp.sync.WebsocketSyncCacheClient;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/29
 */
@Component
public class RuleEventListener {

    @Autowired
    private WebsocketSyncCacheClient client;

    @EventListener
    public void onAdd(RuleAddEvent ruleAddEvent) {
        RouteRuleOperationDTO operationDTO = new RouteRuleOperationDTO(OperationTypeEnum.INSERT, Lists.newArrayList(ruleAddEvent.getAppRuleDTO()));
        client.send(operationDTO);
    }

    @EventListener
    public void onDelete(RuleDeleteEvent ruleDeleteEvent) {
        List<AppRuleDTO> list = Lists.newArrayList(ruleDeleteEvent.getAppRuleDTO());
        RouteRuleOperationDTO operationDTO = new RouteRuleOperationDTO(OperationTypeEnum.DELETE, list);
        client.send(operationDTO);
    }
}
