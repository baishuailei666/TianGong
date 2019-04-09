package com.xlauncher.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

import static org.junit.Assert.*;
/**
 * DivAndOrgUtilTest
 * @author baishuailei
 * @since 2018-07-20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class DivAndOrgUtilTest {
    @Autowired
    DivAndOrgUtil divAndOrgUtil;
    @Test
    public void divisionName() throws Exception {
        System.out.println("divisionName : " + divAndOrgUtil.divisionName(new BigInteger("110105001028")));
    }

    @Test
    public void orgName() throws Exception {
        System.out.println("orgName : " + divAndOrgUtil.orgName("32"));
    }

}