package com.xlauncher.entity;

import java.util.Map;

/**
 * 组件实体类
 * @date 2018-05-10
 * @author 白帅雷
 */
public class Component {
    /** 主键id*/
    private Integer id;
    /** 组件缩写*/
    private String componentAbbr;
    /** 组件名称*/
    private String componentName;
    /** 组件ip地址*/
    private String componentIp;
    /** 组件port端口*/
    private String componentPort;
    /** 组件描述*/
    private String componentDescription;
    /** 组件运行状态*/
    private Integer componentStatus;
    /** 组件额外的配置信息*/
    private Map<String, Object> componentConfiguration;


    public Component() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComponentAbbr() {
        return componentAbbr;
    }

    public void setComponentAbbr(String componentAbbr) {
        this.componentAbbr = componentAbbr;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentIp() {
        return componentIp;
    }

    public void setComponentIp(String componentIp) {
        this.componentIp = componentIp;
    }

    public String getComponentPort() {
        return componentPort;
    }

    public void setComponentPort(String componentPort) {
        this.componentPort = componentPort;
    }

    public String getComponentDescription() {
        return componentDescription;
    }

    public void setComponentDescription(String componentDescription) {
        this.componentDescription = componentDescription;
    }

    public Integer getComponentStatus() {
        return componentStatus;
    }

    public void setComponentStatus(Integer componentStatus) {
        this.componentStatus = componentStatus;
    }

    public Map<String, Object> getComponentConfiguration() {
        return componentConfiguration;
    }

    public void setComponentConfiguration(Map<String, Object> componentConfiguration) {
        this.componentConfiguration = componentConfiguration;
    }

    @Override
    public String toString() {
        return "Component{" +
                "id=" + id +
                ", componentAbbr='" + componentAbbr + '\'' +
                ", componentName='" + componentName + '\'' +
                ", componentIp='" + componentIp + '\'' +
                ", componentPort='" + componentPort + '\'' +
                ", componentDescription='" + componentDescription + '\'' +
                ", componentConfiguration=" + componentConfiguration +
                '}';
    }
}
