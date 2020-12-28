package cn.sp.controller;

import cn.sp.pojo.vo.AppRuleVO;
import cn.sp.pojo.vo.Result;
import cn.sp.service.RuleService;
import cn.sp.sync.WebsocketSyncCacheHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private WebsocketSyncCacheHandler handler;

    @GetMapping("/enabled")
    public Result<List<AppRuleVO>> getEnabledRule() {
        handler.send("aaaaa");
        return Result.success(ruleService.getEnabledRule());
    }
}
