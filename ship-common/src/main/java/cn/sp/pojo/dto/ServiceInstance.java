package cn.sp.pojo.dto;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/25
 */
public class ServiceInstance {

    private String appName;

    private String ip;

    private Integer port;

    private String version;


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
