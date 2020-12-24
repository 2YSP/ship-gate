package cn.sp.service.impl;

import cn.sp.bean.App;
import cn.sp.bean.AppInstance;
import cn.sp.constants.EnabledEnum;
import cn.sp.dto.RegisterAppDTO;
import cn.sp.mapper.AppInstanceMapper;
import cn.sp.mapper.AppMapper;
import cn.sp.service.AppService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/24
 */
@Service
public class AppServiceImpl implements AppService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppServiceImpl.class);

    @Resource
    private AppMapper appMapper;

    @Resource
    private AppInstanceMapper instanceMapper;

    private Gson gson = new GsonBuilder().create();

    @Override
    public void register(RegisterAppDTO registerAppDTO) {
        QueryWrapper<App> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(App::getAppName, registerAppDTO.getAppName());
        App app = appMapper.selectOne(wrapper);
        if (app == null) {
            // first register
            app = new App();
            BeanUtils.copyProperties(registerAppDTO, app);
            app.setEnabled(EnabledEnum.ENABLE.getCode());
            app.setCreatedTime(LocalDateTime.now());
            appMapper.insert(app);
        }
        AppInstance appInstance = new AppInstance();
        appInstance.setAppId(app.getId());
        appInstance.setIp(registerAppDTO.getIp());
        appInstance.setPort(registerAppDTO.getPort());
        appInstance.setVersion(registerAppDTO.getVersion());
        appInstance.setCreatedTime(LocalDateTime.now());
        instanceMapper.insert(appInstance);
        LOGGER.info("register app success,dto:[{}]", gson.toJson(registerAppDTO));
    }


}
