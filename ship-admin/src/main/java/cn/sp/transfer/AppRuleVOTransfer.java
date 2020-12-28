package cn.sp.transfer;

import cn.sp.bean.RouteRule;
import cn.sp.pojo.vo.AppRuleVO;
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

    AppRuleVO mapToVO(RouteRule routeRule);

    List<AppRuleVO> mapToVOList(List<RouteRule> routeRules);
}
