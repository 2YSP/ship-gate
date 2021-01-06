package cn.sp.controller;

import cn.sp.pojo.ChangeStatusDTO;
import cn.sp.pojo.RuleDTO;
import cn.sp.pojo.dto.AppRuleDTO;
import cn.sp.pojo.vo.Result;
import cn.sp.pojo.vo.RuleVO;
import cn.sp.service.RuleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
     * @param ruleDTO
     * @return
     */
    @ResponseBody
    @PostMapping("")
    public Result add(@Validated RuleDTO ruleDTO){
        ruleService.add(ruleDTO);
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

    @ResponseBody
    @PutMapping("/status")
    public Result changeStatus(@RequestBody ChangeStatusDTO statusDTO) {
        ruleService.changeStatus(statusDTO);
        return Result.success();
    }
}
