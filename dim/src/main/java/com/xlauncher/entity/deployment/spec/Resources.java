package com.xlauncher.entity.deployment.spec;

import org.springframework.stereotype.Component;

/**
 * Kubernetes Resources实体类
 * @author YangDengcheng
 * @date 2018/1/25 10:23
 */
@Component
public class Resources {
    private Requests requests;

    public Requests getRequests() {
        return requests;
    }

    public void setRequests(Requests requests) {
        this.requests = requests;
    }
}
