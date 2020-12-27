package cn.sp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by 2YSP on 2020/12/27
 */
@ConfigurationProperties(prefix = "ship.server")
public class ServerConfigProperties {

    private String loadBalance;

    public String getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(String loadBalance) {
        this.loadBalance = loadBalance;
    }
}
