package cn.sp.transfer;

import cn.sp.bean.AppInstance;
import cn.sp.pojo.vo.InstanceVO;
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
public interface InstanceVOTransfer {

    InstanceVOTransfer INSTANCE = Mappers.getMapper(InstanceVOTransfer.class);

    @Mappings({
            @Mapping(target = "createdTime", expression = "java(cn.sp.utils.DateUtils.formatToYYYYMMDDHHmmss(appInstance.getCreatedTime()))")
    })
    InstanceVO mapToVO(AppInstance appInstance);

    List<InstanceVO> mapToVOS(List<AppInstance> appInstances);
}
