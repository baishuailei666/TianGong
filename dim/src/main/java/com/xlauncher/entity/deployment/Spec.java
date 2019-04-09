package com.xlauncher.entity.deployment;

import org.springframework.stereotype.Component;

/**
 * Kubernetes Spec实体类
 * @author YangDengcheng
 * @date 2018/1/25 9:54
 */
@Component
public class Spec {
    private Integer replicas;
    private Selector selector;
    private Template template;

    public Integer getReplicas() {
        return replicas;
    }

    public void setReplicas(Integer replicas) {
        this.replicas = replicas;
    }

    public Selector getSelector() {
        return selector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
}
