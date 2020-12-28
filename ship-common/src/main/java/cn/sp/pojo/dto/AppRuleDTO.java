package cn.sp.pojo.dto;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/28
 */
public class AppRuleDTO {

    private Integer id;

    private Integer appId;

    private String appName;

    private String version;

    private String matchObject;

    private String matchKey;

    private Byte matchMethod;

    private String matchRule;

    private Integer priority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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
