package cn.sp.service;

import cn.sp.pojo.ChangeStatusDTO;
import cn.sp.pojo.RuleDTO;
import cn.sp.pojo.dto.AppRuleDTO;
import cn.sp.pojo.vo.RuleVO;

import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/28
 */
public interface RuleService {

    List<AppRuleDTO> getEnabledRule();

    void add(RuleDTO ruleDTO);

    void delete(Integer id);

    List<RuleVO> queryList(String appName);

    void changeStatus(ChangeStatusDTO statusDTO);
}
