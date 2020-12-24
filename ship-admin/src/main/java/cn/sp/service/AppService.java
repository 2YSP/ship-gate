package cn.sp.service;

import cn.sp.pojo.dto.RegisterAppDTO;

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
}
