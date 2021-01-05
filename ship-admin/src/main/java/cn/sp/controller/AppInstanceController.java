package cn.sp.controller;

import cn.sp.pojo.UpdateWeightDTO;
import cn.sp.pojo.vo.InstanceVO;
import cn.sp.pojo.vo.Result;
import cn.sp.service.AppInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @PutMapping("")
    public Result updateWeight(@RequestBody @Validated UpdateWeightDTO updateWeightDTO){
        instanceService.updateWeight(updateWeightDTO);
        return Result.success();
    }
}
