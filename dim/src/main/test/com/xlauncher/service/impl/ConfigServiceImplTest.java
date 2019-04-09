package com.xlauncher.service.impl;

import com.xlauncher.entity.Service;
import com.xlauncher.service.ConfigService;
import com.xlauncher.util.PropertiesUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author YangDengcheng
 * @time 18-4-19 下午5:46
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ConfigServiceImplTest {
    @Autowired
    ConfigService configService;
    @Autowired
    PropertiesUtil propertiesUtil;

    @Test
    public void addConfigService() throws Exception {
        Service service = new Service();
        service.setSerName("cms");
        service.setSerIp("127.0.0.1");
        service.setSerPort("8000");
        configService.addConfigService(service);

        Service serviceForK8S = new Service();
        serviceForK8S.setSerName("k8s");
        serviceForK8S.setSerIp("127.0.0.1");
        serviceForK8S.setSerPort("8000");
        serviceForK8S.setSerAddress("127.0.0.7:8000/test.tar");
        serviceForK8S.setSerNamespace("testNamespace");
        configService.addConfigService(serviceForK8S);
    }



    @Test
    public void updateConfigService() throws Exception {
        // key和value都是null
//        Service serviceConfig = new Service();
//        serviceConfig.setSerName(null);
//        serviceConfig.setSerIp(null);
//        serviceConfig.setSerPort(null);
//        configService.updateConfigService(serviceConfig);

        // key和value都有
        Service service1 = new Service();
        service1.setSerName("cms");
        service1.setSerIp("127.0.0.1");
        service1.setSerPort("8080");
        configService.updateConfigService(service1);
    }

    @Test
    public void deleteConfigService() throws Exception {
        Service serviceNull = new Service();
        serviceNull.setSerName(null);
        configService.deleteConfigService(serviceNull);

        Service serviceNotNull = new Service();
        serviceNotNull.setSerName("cms");
        configService.deleteConfigService(serviceNotNull);

    }

    @Test
    public void readConfigService() throws Exception {
        String propertiesKey = "cms";
        configService.readConfigService(propertiesKey);
    }

}