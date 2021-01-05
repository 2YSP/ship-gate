package cn.sp.service.impl;

import cn.sp.bean.App;
import cn.sp.bean.AppInstance;
import cn.sp.bean.AppPlugin;
import cn.sp.bean.Plugin;
import cn.sp.constants.EnabledEnum;
import cn.sp.exception.ShipException;
import cn.sp.mapper.AppPluginMapper;
import cn.sp.mapper.PluginMapper;
import cn.sp.pojo.AppPluginDTO;
import cn.sp.pojo.ChangeStatusDTO;
import cn.sp.pojo.vo.AppVO;
import cn.sp.pojo.dto.AppInfoDTO;
import cn.sp.pojo.dto.RegisterAppDTO;
import cn.sp.mapper.AppInstanceMapper;
import cn.sp.mapper.AppMapper;
import cn.sp.pojo.dto.ServiceInstance;
import cn.sp.pojo.dto.UnregisterAppDTO;
import cn.sp.service.AppService;
import cn.sp.transfer.AppInstanceTransfer;
import cn.sp.transfer.AppVOTransfer;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Resource
    private AppPluginMapper appPluginMapper;

    @Resource
    private PluginMapper pluginMapper;

    private Gson gson = new GsonBuilder().create();

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(RegisterAppDTO registerAppDTO) {
        App app = queryByAppName(registerAppDTO.getAppName());
        Integer appId;
        if (app == null) {
            // first register
            appId = addApp(registerAppDTO);
        } else {
            appId = app.getId();
        }
        AppInstance instance = query(appId, registerAppDTO.getIp(), registerAppDTO.getPort(), registerAppDTO.getVersion());
        if (instance == null) {
            AppInstance appInstance = new AppInstance();
            appInstance.setAppId(appId);
            appInstance.setIp(registerAppDTO.getIp());
            appInstance.setPort(registerAppDTO.getPort());
            appInstance.setVersion(registerAppDTO.getVersion());
            appInstance.setCreatedTime(LocalDateTime.now());
            instanceMapper.insert(appInstance);
        }
        LOGGER.info("register app success,dto:[{}]", gson.toJson(registerAppDTO));
    }

    private AppInstance query(Integer appId, String ip, Integer port, String version) {
        QueryWrapper<AppInstance> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(AppInstance::getAppId, appId)
                .eq(AppInstance::getVersion, version)
                .eq(AppInstance::getIp, ip)
                .eq(AppInstance::getPort, port);
        return instanceMapper.selectOne(wrapper);
    }

    private Integer addApp(RegisterAppDTO registerAppDTO) {
        App app = new App();
        BeanUtils.copyProperties(registerAppDTO, app);
        app.setEnabled(EnabledEnum.ENABLE.getCode());
        app.setCreatedTime(LocalDateTime.now());
        appMapper.insert(app);
        bindPlugins(app);
        return app.getId();
    }

    /**
     * bind app with all plugins
     *
     * @param app
     */
    private void bindPlugins(App app) {
        List<Plugin> plugins = pluginMapper.selectList(new QueryWrapper<>());
        if (CollectionUtils.isEmpty(plugins)) {
            throw new ShipException("must init plugins first!");
        }
        plugins.forEach(p -> {
            AppPlugin appPlugin = new AppPlugin();
            appPlugin.setAppId(app.getId());
            appPlugin.setPluginId(p.getId());
            appPlugin.setEnabled(EnabledEnum.ENABLE.getCode());
            appPluginMapper.insert(appPlugin);
        });
    }

    @Override
    public void unregister(UnregisterAppDTO dto) {
        App app = queryByAppName(dto.getAppName());
        if (app == null) {
            return;
        }
        QueryWrapper<AppInstance> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(AppInstance::getAppId, app.getId())
                .eq(AppInstance::getVersion, dto.getVersion())
                .eq(AppInstance::getIp, dto.getIp())
                .eq(AppInstance::getPort, dto.getPort());
        instanceMapper.delete(wrapper);
        LOGGER.info("unregister app instance success,dto:[{}]", gson.toJson(dto));
    }

    private App queryByAppName(String appName) {
        QueryWrapper<App> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(App::getAppName, appName);
        App app = appMapper.selectOne(wrapper);
        return app;
    }

    @Override
    public List<AppInfoDTO> getAppInfos(List<String> appNames) {
        List<App> apps = getAppList(appNames);
        List<Integer> appIds = apps.stream().map(App::getId).collect(Collectors.toList());
        QueryWrapper<AppInstance> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(AppInstance::getAppId, appIds);
        List<AppInstance> appInstances = instanceMapper.selectList(wrapper);
        List<AppPluginDTO> appPluginDTOS = appPluginMapper.queryEnabledPlugins(appIds);
        if (CollectionUtils.isEmpty(appInstances) || CollectionUtils.isEmpty(appPluginDTOS)) {
            LOGGER.info("no app infos....");
            return Lists.newArrayList();
        }
        return buildAppInfos(apps, appInstances, appPluginDTOS);
    }

    private List<AppInfoDTO> buildAppInfos(List<App> apps, List<AppInstance> appInstances, List<AppPluginDTO> appPluginDTOS) {
        List<AppInfoDTO> list = Lists.newArrayList();
        apps.forEach(app -> {
            AppInfoDTO appInfoDTO = new AppInfoDTO();
            appInfoDTO.setAppId(app.getId());
            appInfoDTO.setAppName(app.getAppName());
            appInfoDTO.setEnabled(app.getEnabled());
            List<String> enabledPlugins = appPluginDTOS.stream().filter(r -> r.getAppId().equals(app.getId()))
                    .map(AppPluginDTO::getCode)
                    .collect(Collectors.toList());
            appInfoDTO.setEnabledPlugins(enabledPlugins);
            List<AppInstance> instances = appInstances.stream().filter(r -> r.getAppId().equals(app.getId())).collect(Collectors.toList());
            List<ServiceInstance> serviceList = AppInstanceTransfer.INSTANCE.mapToServiceList(instances);
            appInfoDTO.setInstances(serviceList);
            list.add(appInfoDTO);
        });
        return list;
    }

    private List<App> getAppList(List<String> appNames) {
        QueryWrapper<App> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(App::getAppName, appNames);
        return appMapper.selectList(wrapper);
    }

    @Override
    public List<AppVO> getList() {
        QueryWrapper<App> wrapper = new QueryWrapper<>();
        List<App> apps = appMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(apps)) {
            return Lists.newArrayList();
        }
        List<AppVO> appVOS = AppVOTransfer.INSTANCE.mapToVOList(apps);
        List<Integer> appIds = appVOS.stream().map(AppVO::getId).collect(Collectors.toList());
        QueryWrapper<AppInstance> query = Wrappers.query();
        query.lambda().in(AppInstance::getAppId, appIds);
        List<AppInstance> instances = instanceMapper.selectList(query);
        if (CollectionUtils.isEmpty(instances)) {
            appVOS.forEach(appVO -> appVO.setInstanceNum(0));
        } else {
            Map<Integer, List<AppInstance>> map = instances.stream().collect(Collectors.groupingBy(AppInstance::getAppId));
            appVOS.forEach(appVO -> appVO.setInstanceNum(map.getOrDefault(appVO.getId(), Lists.newArrayList()).size()));
        }
        return appVOS;
    }

    /**
     * if disable the app,it will make all instances offline
     * @param statusDTO
     */
    @Override
    public void updateEnabled(ChangeStatusDTO statusDTO) {
        App app = new App();
        app.setId(statusDTO.getId());
        app.setEnabled(statusDTO.getEnabled());
        appMapper.updateById(app);
    }

    @Override
    public void delete(Integer id) {
        QueryWrapper<AppInstance> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(AppInstance::getAppId,id);
        List<AppInstance> instances = instanceMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(instances)){
            throw new ShipException("该服务存在实例不可删除！");
        }
        appMapper.deleteById(id);
    }
}
