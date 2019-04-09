package com.xlauncher.entity.deployment;

import org.springframework.stereotype.Component;

/**
 * Kubernetes Selector实体类
 * @author YangDengcheng
 * @date 2018/1/25 9:59
 */
@Component
public class Selector {
    private MatchLabels matchLabels;

    public MatchLabels getMatchLabels() {
        return matchLabels;
    }

    public void setMatchLabels(MatchLabels matchLabels) {
        this.matchLabels = matchLabels;
    }
}
