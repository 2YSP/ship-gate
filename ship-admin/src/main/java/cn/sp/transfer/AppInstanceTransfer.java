package cn.sp.transfer;

import cn.sp.bean.AppInstance;
import cn.sp.pojo.dto.ServiceInstance;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/30
 */
@Mapper
public interface AppInstanceTransfer {

    AppInstanceTransfer INSTANCE = Mappers.getMapper(AppInstanceTransfer.class);

    ServiceInstance mapToService(AppInstance appInstance);

    List<ServiceInstance> mapToServiceList(List<AppInstance> appInstances);
}
