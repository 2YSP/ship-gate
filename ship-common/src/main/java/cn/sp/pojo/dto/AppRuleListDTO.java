package cn.sp.pojo.dto;

import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/28
 */
public class AppRuleListDTO {

    private List<AppRuleDTO> list;

    public AppRuleListDTO() {
    }

    public AppRuleListDTO(List<AppRuleDTO> list) {
        this.list = list;
    }

    public List<AppRuleDTO> getList() {
        return list;
    }

    public void setList(List<AppRuleDTO> list) {
        this.list = list;
    }
}
