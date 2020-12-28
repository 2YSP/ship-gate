package cn.sp.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ThreadFactory;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/28
 */
public class ShipThreadFactory {

    private String name;

    private Boolean daemon;

    public ShipThreadFactory(String name, Boolean daemon) {
        this.name = name;
        this.daemon = daemon;
    }

    public ThreadFactory create() {
        return new ThreadFactoryBuilder().setNameFormat(this.name + "-%d").setDaemon(this.daemon).build();
    }

}
