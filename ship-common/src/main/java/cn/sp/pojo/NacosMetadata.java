package cn.sp.pojo;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/30
 */
public class NacosMetadata {

    private String appName;

    private String version;

    private String plugins;


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

    public String getPlugins() {
        return plugins;
    }

    public void setPlugins(String plugins) {
        this.plugins = plugins;
    }
}
