package com.xlauncher.service.impl;

import com.xlauncher.entity.configmap.ConfigMap;
import com.xlauncher.entity.configmap.Data;
import com.xlauncher.entity.configmap.MetaDataInConfigMap;
import com.xlauncher.entity.deployment.*;
import com.xlauncher.entity.deployment.spec.*;
import com.xlauncher.service.CreateModelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YangDengcheng
 * @time 18-4-20 上午11:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CreateModelServiceImplTest {
    private static String k8sUrl = "8.11.0.71:30080";

    @Autowired
    CreateModelService createModelService;

    @Test
    public void createDeployment() throws Exception {

        // 初始化Deployment所需参数
        Deployment deployment = new Deployment();
        Labels labels = new Labels();
        Metadata metadataOut = new Metadata();
        MatchLabels matchLabels = new MatchLabels();
        Selector selector = new Selector();
        Spec specOut = new Spec();
        Template template = new Template();
        MetadataIn metadataIn = new MetadataIn();
        SpecIn specIn = new SpecIn();
        Containers containers = new Containers();
        NodeSelector nodeSelector = new NodeSelector();
        Requests requests = new Requests();
        Resources resources = new Resources();
        VolumeMounts volumeMounts = new VolumeMounts();
        Volumes volumes = new Volumes();
        ConfigMapIn configMapIn = new ConfigMapIn();

        // 设置外部metadata的值
        labels.setApp("channel-1234567");
        metadataOut.setName("channel-1234567");
        metadataOut.setNamespace("apollo-prj");
        metadataOut.setLabels(labels);

        // 设置外部spec的参数值
        matchLabels.setApp("channel-1234567");
        selector.setMatchLabels(matchLabels);

        // 设置内部metadata的参数值
        Map<String,String> labelMap = new HashMap<>();
        labelMap.put("app","channel-1234567");
        metadataIn.setLabels(labelMap);

        //设置内部spec的参数值
        //设置CPU
        requests.setCpu("1024m");

        //设置Memory
        requests.setMemory("512Mi");
        resources.setRequests(requests);

        //设置镜像地址
        containers.setImage("8.11.0.61:30402://tomcat:1.2");

        //设置容器的名称
        containers.setName("channel-1234567");
        containers.setResources(resources);

        //设置volumeMounts
        volumeMounts.setName("config-volume1");
        volumeMounts.setMountPath("/config");
        List<VolumeMounts> volumeMountsList = new ArrayList<>();
        volumeMountsList.add(volumeMounts);
        containers.setVolumeMounts(volumeMountsList);

        //设置容器内容
        List<Containers> containersList = new ArrayList<>();
        containersList.add(containers);

        nodeSelector.setApolloNamespace("apollo-prj");

        //设置configMap的参数以及volumes参数
        configMapIn.setDefaultMode(123);
        configMapIn.setName("channel-1234567");
        volumes.setConfigMap(configMapIn);
        volumes.setName("config-volume1");
        List<Volumes> volumesList = new ArrayList<>();
        volumesList.add(volumes);
        specIn.setNodeSelector(nodeSelector);
        specIn.setContainers(containersList);
        specIn.setVolumes(volumesList);

        template.setSpec(specIn);
        template.setMetadata(metadataIn);

        specOut.setReplicas(1);                                                 // 设置副本控制器数量
        specOut.setSelector(selector);
        specOut.setTemplate(template);

        deployment.setMetadata(metadataOut);
        deployment.setSpec(specOut);

        createModelService.createDeployment(k8sUrl,deployment);
    }

    @Test
    public void createConfigMap() throws Exception {

        // 创建configMap
        ConfigMap configMap = new ConfigMap();
        Data data = new Data();
        MetaDataInConfigMap metaDataInConfigMap = new MetaDataInConfigMap();

        // 设置configMap的所需参数
        data.setProperties("testConfigMap");
        metaDataInConfigMap.setName("channel-1234567");
        metaDataInConfigMap.setNamespace("apollo-prj");
        configMap.setData(data);
        configMap.setMetadata(metaDataInConfigMap);

        createModelService.createConfigMap(k8sUrl,configMap);
    }

}