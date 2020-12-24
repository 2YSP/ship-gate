package cn.sp;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by 2YSP on 2020/12/21
 */
@ConfigurationProperties(prefix = "ship.http")
public class ClientConfigProperties {
    /**
     * 启动端口号
     */
    private Integer port;
    /**
     * 请求根路径
     */
    private String contextPath;
    /**
     * 应用名称
     */
    private String appName;

    /**
     * 接口版本号
     */
    private String version;

    private String adminUrl;

    public String getAdminUrl() {
        return adminUrl;
    }

    public void setAdminUrl(String adminUrl) {
        this.adminUrl = adminUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
