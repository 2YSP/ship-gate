package cn.sp.controller;

import cn.sp.pojo.dto.AppRuleDTO;
import cn.sp.pojo.vo.AppRuleListVO;
import cn.sp.pojo.vo.Result;
import cn.sp.service.RuleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/25
 */
@RestController
@RequestMapping("/rule")
public class RuleController {

    @Resource
    private RuleService ruleService;

    @GetMapping("/enabled")
    public Result<AppRuleListVO> getEnabledRule() {
        AppRuleListVO listVO = new AppRuleListVO();
        listVO.setList(ruleService.getEnabledRule());
        return Result.success(listVO);
    }

    /**
     * add new route rule
     *
     * @param appRuleDTO
     * @return
     */
    @PostMapping("")
    public Result add(@RequestBody @Validated AppRuleDTO appRuleDTO) {
        ruleService.add(appRuleDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        ruleService.delete(id);
        return Result.success();
    }
}
