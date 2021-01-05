package cn.sp.service;

import cn.sp.pojo.UpdateWeightDTO;
import cn.sp.pojo.vo.InstanceVO;

import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2021/1/5
 */
public interface AppInstanceService {
    /**
     * query instances by appId
     * @param appId
     * @return
     */
    List<InstanceVO> queryList(Integer appId);

    void updateWeight(UpdateWeightDTO updateWeightDTO);
}
