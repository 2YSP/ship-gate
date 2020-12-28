package cn.sp.controller;

import cn.sp.pojo.dto.AppRuleDTO;
import cn.sp.pojo.vo.AppRuleListVO;
import cn.sp.pojo.vo.Result;
import cn.sp.service.RuleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
