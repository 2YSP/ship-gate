package cn.sp.transfer;

import cn.sp.bean.RouteRule;
import cn.sp.pojo.vo.RuleVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2021/1/5
 */
@Mapper
public interface RuleVOTransfer {

    RuleVOTransfer INSTANCE = Mappers.getMapper(RuleVOTransfer.class);

    @Mappings({
            @Mapping(target = "createdTime", expression = "java(cn.sp.utils.DateUtils.formatToYYYYMMDDHHmmss(rule.getCreatedTime()))")
    })
    RuleVO mapToVO(RouteRule rule);

    List<RuleVO> mapToVOList(List<RouteRule> rules);
}
