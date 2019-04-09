package com.xlauncher.util;

import com.xlauncher.dao.dimdao.IChannelDao;
import com.xlauncher.entity.Channel;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * 说明：在tomcat服务启动时检查在用channel的threadId，并重新连接
 * tomcat服务终止时，使在用channel的threadId置为0;
 * @author YangDengcheng
 * @time 18-7-27 上午11:37
 */
@Component
public class TomcatListen implements ServletContextListener{

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(TomcatListen.class);
    public static ThreadGroup tg = new ThreadGroup("channelGroup");

    {
        tg = Thread.currentThread().getThreadGroup();
    }

    /**
     * 监听tomcat启动并查询数据库重新拉起channel
     *
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 获取容器和相关的bean
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        LOGGER.info("[TOMCAT初始化] - 获取容器和相关的bean：" +ac);
        IChannelDao channelDao = (IChannelDao) ac.getBean("iChannelDao");
        List<Channel> channelList = channelDao.queryAllChannelIn();
        channelList.forEach(channelIn -> {
            // 创建线程
            ThreadUtil threadUtil = new ThreadUtil(channelIn);
            Thread thread = new Thread(tg,threadUtil,channelIn.getChannelId());
            thread.start();
            LOGGER.info("创建线程-获取线程ID, 并存入数据库.channelId:" + channelIn.getChannelId()  + ", channelName."+ channelIn.getChannelName()  + ", threadId."+ thread.getId());
            // 获取线程ID并存入数据库
            long threadId = thread.getId();
            channelIn.setChannelThreadId((int)threadId);
            LOGGER.info("***channelIn:" + channelIn);
            channelDao.updateChannelMsg(channelIn);
        });
        LOGGER.info("[TOMCAT启动] - channelList : "+ channelList.size());
    }

    /**
     * 监听tomcat关闭并中断channel同时设置threadId为0
     *
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // 获取容器和相关的bean
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        LOGGER.info("[TOMCAT关闭] - 获取容器和相关的bean");
        IChannelDao channelDao = (IChannelDao) ac.getBean("iChannelDao");
        List<Channel> channelList = channelDao.queryAllChannelIn();
        channelList.forEach(channelOut -> {
            // 中断通信
            int channelThreadId = channelDao.queryChannelThread(channelOut.getChannelId());
            // 中断线程（中断和ICS和CMS的通信）
            LOGGER.info("[中断线程 channelId, channelName, channelThreadId]" + channelOut.getChannelId()  + ", "+ channelOut.getChannelName()  + ", "+channelThreadId);
            Thread[] threads = new Thread[(int) (tg.activeCount())];
            int count = tg.enumerate(threads,true);
            for (int i=0;i<count;i++){
                if (threads[i].getId() == channelThreadId){
                    threads[i].interrupt();
                }
            }
            // 设置threadId为0;
            channelOut.setChannelThreadId(0);
            LOGGER.info("***channelOut:" + channelOut);
            channelDao.updateChannelMsg(channelOut);
        });
        LOGGER.info("[TOMCAT关闭] - channelList : "+ channelList.size());
    }
}
