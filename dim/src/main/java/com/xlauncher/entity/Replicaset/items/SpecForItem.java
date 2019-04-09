package com.xlauncher.entity.Replicaset.items;

import com.xlauncher.entity.deployment.Template;

/**
 * @author YangDengcheng
 * @date 2018/1/26 16:48
 */
public class SpecForItem {
    private Integer replicas;
    private SelectorForSpec selector;
    private TemplateForSpec template;

    public Integer getReplicas() {
        return replicas;
    }

    public void setReplicas(Integer replicas) {
        this.replicas = replicas;
    }

    public SelectorForSpec getSelector() {
        return selector;
    }

    public void setSelector(SelectorForSpec selector) {
        this.selector = selector;
    }

    public TemplateForSpec getTemplate() {
        return template;
    }

    public void setTemplate(TemplateForSpec template) {
        this.template = template;
    }
}
