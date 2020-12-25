package cn.sp.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/25
 */
@TableName("t_app_plugin")
public class AppPlugin {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer appId;

    private Integer pluginId;

    private Byte enabled;

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

    public Integer getPluginId() {
        return pluginId;
    }

    public void setPluginId(Integer pluginId) {
        this.pluginId = pluginId;
    }

    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }
}
