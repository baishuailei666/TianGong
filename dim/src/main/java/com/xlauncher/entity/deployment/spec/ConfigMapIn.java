package com.xlauncher.entity.deployment.spec;

/**
 * @author YangDengcheng
 * @date 2018/2/28 14:09
 */
public class ConfigMapIn {
    private Integer defaultMode;
    private String name;

    public Integer getDefaultMode() {
        return defaultMode;
    }

    public void setDefaultMode(Integer defaultMode) {
        this.defaultMode = defaultMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
