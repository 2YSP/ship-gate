package cn.sp.service.impl;

import cn.sp.bean.App;
import cn.sp.bean.AppInstance;
import cn.sp.mapper.AppInstanceMapper;
import cn.sp.mapper.AppMapper;
import cn.sp.pojo.vo.InstanceVO;
import cn.sp.service.AppInstanceService;
import cn.sp.transfer.InstanceVOTransfer;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2021/1/5
 */
@Service
public class AppInstanceServiceImpl implements AppInstanceService {

    @Resource
    private AppInstanceMapper instanceMapper;

    @Resource
    private AppMapper appMapper;

    @Override
    public List<InstanceVO> queryList(Integer appId) {
        App app = appMapper.selectById(appId);
        QueryWrapper<AppInstance> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(AppInstance::getAppId, appId);
        List<AppInstance> instanceList = instanceMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(instanceList)) {
            return Lists.newArrayList();
        }
        List<InstanceVO> voList = InstanceVOTransfer.INSTANCE.mapToVOS(instanceList);
        voList.forEach(vo -> vo.setAppName(app.getAppName()));
        return voList;
    }
}
