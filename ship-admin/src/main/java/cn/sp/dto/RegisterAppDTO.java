package cn.sp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/24
 */
public class RegisterAppDTO {
    @NotEmpty(message = "appName is required")
    private String appName;

    @NotEmpty(message = "contextPath is required")
    private String contextPath;

    @NotEmpty(message = "version is required")
    private String version;

    @NotEmpty(message = "ip is required")
    private String ip;

    @NotNull(message = "port is required")
    private Integer port;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
}
