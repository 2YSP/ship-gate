package cn.sp.service;

import cn.sp.pojo.dto.RegisterAppDTO;
import cn.sp.pojo.dto.UnregisterAppDTO;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/24
 */
public interface AppService {

    /**
     * register app
     * @param registerAppDTO
     */
    void register(RegisterAppDTO registerAppDTO);

    /**
     * unregister app instance
     * @param unregisterAppDTO
     */
    void unregister(UnregisterAppDTO unregisterAppDTO);
}
