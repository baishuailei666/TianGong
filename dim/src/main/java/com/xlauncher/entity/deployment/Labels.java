package com.xlauncher.entity.deployment;

import org.springframework.stereotype.Component;

/**
 * Kubernetes Labels实体类
 * @author YangDengcheng
 * @date 2018/1/25 10:52
 */
@Component
public class Labels {
    private String app;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
