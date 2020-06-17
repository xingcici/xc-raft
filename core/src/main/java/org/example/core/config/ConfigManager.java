package org.example.core.config;

import org.example.common.life.LifeCycle;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author haifeng.pang [panghaifeng@weidian.com]
 * @version 1.0 : ConfigManager v0.1 2020/6/16 17:27 haifeng.pang Exp $
 **/
public class ConfigManager implements LifeCycle {

    private ConcurrentHashMap<String, Object> configMap = new ConcurrentHashMap<>();

    private boolean isStart;

    @Override
    public void startup() {
        configMap.put("", "");
    }

    @Override
    public void shutdown() {

    }

    public boolean isStart() {
        return isStart;
    }
}
