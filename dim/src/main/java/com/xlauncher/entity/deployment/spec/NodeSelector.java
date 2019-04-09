package com.xlauncher.entity.deployment.spec;

import org.springframework.stereotype.Component;

/**
 * Kubernetes NodeSelector实体类
 * @author YangDengcheng
 * @date 2018/1/25 10:30
 */
@Component
public class NodeSelector {
    private String apolloNamespace;

    public String getApolloNamespace() {
        return apolloNamespace;
    }

    public void setApolloNamespace(String apolloNamespace) {
        this.apolloNamespace = apolloNamespace;
    }
}
