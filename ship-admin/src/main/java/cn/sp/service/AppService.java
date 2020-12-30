package cn.sp.service;

import cn.sp.pojo.dto.AppInfoDTO;
import cn.sp.pojo.dto.RegisterAppDTO;
import cn.sp.pojo.dto.UnregisterAppDTO;

import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/24
 */
public interface AppService {

    /**
     * register app
     *
     * @param registerAppDTO
     */
    void register(RegisterAppDTO registerAppDTO);

    /**
     * unregister app instance
     *
     * @param unregisterAppDTO
     */
    void unregister(UnregisterAppDTO unregisterAppDTO);


    List<AppInfoDTO> getAppInfos(List<String> appNames);
}
