package cn.sp.service;

import cn.sp.pojo.dto.AppRuleDTO;

import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/28
 */
public interface RuleService {

    List<AppRuleDTO> getEnabledRule();
}
