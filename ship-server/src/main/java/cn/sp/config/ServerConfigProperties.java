package cn.sp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by 2YSP on 2020/12/27
 */
@ConfigurationProperties(prefix = "ship.gate")
public class ServerConfigProperties {

    private String loadBalance;

    private Long timeOutMillis;
    /**
     * 单位s
     */
    private Long DataRefreshInterval;

    public Long getDataRefreshInterval() {
        return DataRefreshInterval;
    }

    public void setDataRefreshInterval(Long dataRefreshInterval) {
        DataRefreshInterval = dataRefreshInterval;
    }

    public Long getTimeOutMillis() {
        return timeOutMillis;
    }

    public void setTimeOutMillis(Long timeOutMillis) {
        this.timeOutMillis = timeOutMillis;
    }

    public String getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(String loadBalance) {
        this.loadBalance = loadBalance;
    }
}
