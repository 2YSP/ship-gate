package cn.sp.transfer;

import cn.sp.bean.RouteRule;
import cn.sp.pojo.dto.AppRuleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/28
 */
@Mapper
public interface AppRuleVOTransfer {

    AppRuleVOTransfer INSTANCE = Mappers.getMapper(AppRuleVOTransfer.class);

    AppRuleDTO mapToVO(RouteRule routeRule);

    List<AppRuleDTO> mapToVOList(List<RouteRule> routeRules);
}
