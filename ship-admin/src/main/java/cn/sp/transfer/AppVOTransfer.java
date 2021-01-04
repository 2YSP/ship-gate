package cn.sp.transfer;

import cn.sp.bean.App;
import cn.sp.pojo.AppVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2021/1/4
 */
@Mapper
public interface AppVOTransfer {

    AppVOTransfer INSTANCE = Mappers.getMapper(AppVOTransfer.class);

    @Mappings({
            @Mapping(target = "createdTime", expression = "java(cn.sp.utils.DateUtils.formatToYYYYMMDDHHmmss(app.getCreatedTime()))")
    })
    AppVO mapToVO(App app);

    List<AppVO> mapToVOList(List<App> appList);
}
