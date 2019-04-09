package com.xlauncher.entity.deployment;

import org.springframework.stereotype.Component;

/**
 * Kubernetes MatchLabels参数
 * @author YangDengcheng
 * @date 2018/1/25 10:00
 */
@Component
public class MatchLabels {
    private String app;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
