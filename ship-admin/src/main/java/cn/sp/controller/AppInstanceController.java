package cn.sp.controller;

import cn.sp.pojo.vo.InstanceVO;
import cn.sp.service.AppInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2021/1/5
 */
@Controller
@RequestMapping("/app/instance")
public class AppInstanceController {

    @Autowired
    private AppInstanceService instanceService;

    @GetMapping("/list")
    public String list(@RequestParam("appId") Integer appId, ModelMap map) {
        List<InstanceVO> instanceVOS = instanceService.queryList(appId);
        map.put("instanceVOS", instanceVOS);
        return "instance";
    }
}
