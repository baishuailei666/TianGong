package com.xlauncher.util;

import com.xlauncher.dao.EventAlertDao;
import com.xlauncher.entity.EventAlert;
import com.xlauncher.util.synsunnyitec.PushEventToSunnyintec;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-*.xml"})
@Transactional
public class PushEventToSunnyintecTest {
    @Autowired
    private PushEventToSunnyintec pushEventToSunnyintec;
    @Autowired
    private EventAlertDao eventAlertDao;
    @Test
    public void sunnyintecLogin() throws Exception {
        System.out.println("sunnyintecLogin ticket:" + pushEventToSunnyintec.sunnyintecLogin().get("ticket"));
    }

    @Test
    public void sunnyintecSubmitEvent() throws Exception {
        EventAlert eventAlert = eventAlertDao.getAlertByEventId(1053);
        String ticket = (String) pushEventToSunnyintec.sunnyintecLogin().get("ticket");
        System.out.println("__________ ticket __________ " + ticket);
        System.out.println(" __________ sunnyintecSubmitEvent __________ " + pushEventToSunnyintec.sunnyintecSubmitEvent(eventAlert, ticket));
    }

}