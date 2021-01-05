package cn.sp.pojo;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2021/1/5
 */
public class ChangeStatusDTO {

    private Integer id;

    private Byte enabled;

    private String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }
}
