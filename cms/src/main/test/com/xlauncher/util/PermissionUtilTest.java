package com.xlauncher.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @Auther :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/9/20 0020
 * @Desc :
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class PermissionUtilTest {
    @Autowired
    PermissionUtil permissionUtil;
    @Test
    public void getPermission() throws Exception {
        System.out.println("getPermission:" + permissionUtil.getPermission(1));
    }

}