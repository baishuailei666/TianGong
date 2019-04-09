package com.xlauncher.service.impl;

import com.xlauncher.util.synsunnyitec.PushEventToSunnyintec;
import com.xlauncher.util.ServiceUtil;
import com.xlauncher.util.synsunnyitec.SynSunnyintecProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;


import javax.servlet.ServletContext;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 系统初始化监听器
 * @author 张霄龙
 * @since 2018-04-18
 */
@Component
public class StartupListerner implements ApplicationContextAware, ServletContextAware,
        InitializingBean, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private SynSunnyintec synSunnyintec;
    @Autowired
    private ServiceUtil serviceUtil;
    @Autowired
    private PushEventToSunnyintec pushEventToSunnyintec;
    @Autowired
    private SynSunnyintecProperties properties;
    private static Logger logger = Logger.getLogger(StartupListerner.class);

    /**
     * 初始化监听器 ApplicationContextAware
     * @param applicationContext Spring上下文句柄
     * @throws BeansException Bean类异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("1 => StartupListener.setApplicationContext");
        try {
            logger.debug(" ________初始化监听器同步第三方数据...");
//            serviceUtil.synchro();
            Runnable runnable = () -> {
                try {
                    Map map = pushEventToSunnyintec.sunnyintecLogin();
                    if (!map.get("code").equals(200)) {
                        logger.warn(" sunnyintecLogin error!" + map);
                        return;
                    }
                    String ticket = (String) map.get("ticket");
                    logger.info(" ________ticket_______ [" + ticket + "]");
                    logger.debug("________启动线程定时同步组织 synOrg...");
                    synSunnyintec.synOrg(ticket);
                    logger.debug("________启动线程定时同步设备 synDevice...");
                    synSunnyintec.synDevice(ticket);
                    logger.debug("________启动线程定时同步通道 synChannel...");
                    synSunnyintec.synChannel(ticket);
                } catch (Exception e) {
                    logger.error("syn服务异常！" + e.getMessage());
                }
            };
            ScheduledExecutorService service = Executors
                    .newSingleThreadScheduledExecutor();
            // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
//            service.scheduleAtFixedRate(runnable, Long.parseLong(properties.synTime()), Long.parseLong(properties.synTime()), TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * ServletContextAware
     * @param servletContext
     */
    @Override
    public void setServletContext(ServletContext servletContext) {
        System.out.println("2 => ServletContextAware.setServletContext");
    }

    /**
     * InitializingBean
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("3 => InitializingBean.afterPropertiestSet");
    }

    /**
     * ApplicationListener
     * @param contextRefreshedEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("4.1 => ApplicationListener.onApplicationEvent");
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            System.out.println("4.2 => ApplicationListener.onApplicationEvent");
        }
    }
}
