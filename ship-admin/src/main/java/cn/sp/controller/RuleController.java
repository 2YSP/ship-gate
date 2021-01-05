package cn.sp.controller;

import cn.sp.pojo.dto.AppRuleDTO;
import cn.sp.pojo.vo.Result;
import cn.sp.pojo.vo.RuleVO;
import cn.sp.service.RuleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/25
 */
@Controller
@RequestMapping("/rule")
public class RuleController {

    @Resource
    private RuleService ruleService;

//    @GetMapping("/enabled")
//    public Result<AppRuleListVO> getEnabledRule() {
//        AppRuleListVO listVO = new AppRuleListVO();
//        listVO.setList(ruleService.getEnabledRule());
//        return Result.success(listVO);
//    }

    /**
     * add new route rule
     *
     * @param appRuleDTO
     * @return
     */
    @ResponseBody
    @PostMapping("")
    public Result add(@RequestBody @Validated AppRuleDTO appRuleDTO) {
        ruleService.add(appRuleDTO);
        return Result.success();
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        ruleService.delete(id);
        return Result.success();
    }

    @GetMapping("/list")
    public String list(ModelMap map, @RequestParam(name = "appName", required = false) String appName) {
        List<RuleVO> ruleVOS = ruleService.queryList(appName);
        map.put("ruleVOS", ruleVOS);
        map.put("appName", appName);
        return "rule";
    }
}
