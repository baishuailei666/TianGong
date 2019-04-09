package com.xlauncher.util;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * SocketClientTest
 * @author baishuailei
 * @since 2018-07-30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class SocketClientTest {
    private static final Logger log = Logger.getLogger(SocketClientTest.class);

    @Test
    public void testSocketAppender() throws Exception{
        for (int i = 0; i < 5; i++) {
            log.warn("this is the WARN message.");
            log.debug("this is the DEBUG message.");
            log.info("this is the INFO message.");
            log.error("this is the ERROR message.");
        }
    }
}
