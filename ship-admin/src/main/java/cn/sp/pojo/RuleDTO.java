package cn.sp.pojo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2021/1/6
 */
public class RuleDTO {

    @NotNull(message = "appId不能为空")
    private Integer appId;

    @NotEmpty(message = "name不能为空")
    private String name;

    @NotEmpty(message = "version不能为空")
    private String version;

    @NotEmpty(message = "matchObject不能为空")
    private String matchObject;

    private String matchKey;

    private Byte matchMethod;

    private String matchRule;

    @NotNull(message = "priority不能为空")
    private Integer priority;

    private Byte enabled;

    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMatchObject() {
        return matchObject;
    }

    public void setMatchObject(String matchObject) {
        this.matchObject = matchObject;
    }

    public String getMatchKey() {
        return matchKey;
    }

    public void setMatchKey(String matchKey) {
        this.matchKey = matchKey;
    }

    public Byte getMatchMethod() {
        return matchMethod;
    }

    public void setMatchMethod(Byte matchMethod) {
        this.matchMethod = matchMethod;
    }

    public String getMatchRule() {
        return matchRule;
    }

    public void setMatchRule(String matchRule) {
        this.matchRule = matchRule;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
