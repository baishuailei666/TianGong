package com.xlauncher.service.impl;

import com.xlauncher.service.DeleteModelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author YangDengcheng
 * @time 18-4-20 上午11:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class DeleteModelServiceImplTest {
    @Autowired
    DeleteModelService deleteModelService;

    private static String deleteApplicationUrl = "8.11.0.71:30080/test";

    @Test
    public void deleteApplication() throws Exception {
        deleteModelService.deleteApplication(deleteApplicationUrl);
    }

}