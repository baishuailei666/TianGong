package com.xlauncher.service.impl;

import com.xlauncher.service.GetModelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author YangDengcheng
 * @time 18-4-20 上午11:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class GetModelServiceImplTest {
    @Autowired
    GetModelService getModelService;

    @Test
    public void getReplicaset() throws Exception {
        String nullOfUrl = null;
        getModelService.getReplicaset(nullOfUrl);

        // TODO 需要k8s平台支持
        String replicasetUrl = "8.11.0.71:30080/test";
        getModelService.getReplicaset(replicasetUrl);
    }

}