package cn.sp.pojo.dto;

import cn.sp.constants.OperationTypeEnum;

import java.util.List;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/28
 */
public class RouteRuleOperationDTO {
    /**
     * {@link OperationTypeEnum}
     */
    private String operationType;

    private List<AppRuleDTO> ruleList;

    public RouteRuleOperationDTO() {
    }

    public RouteRuleOperationDTO(OperationTypeEnum operationTypeEnum, List<AppRuleDTO> ruleList) {
        this.operationType = operationTypeEnum.getCode();
        this.ruleList = ruleList;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public List<AppRuleDTO> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<AppRuleDTO> ruleList) {
        this.ruleList = ruleList;
    }
}
