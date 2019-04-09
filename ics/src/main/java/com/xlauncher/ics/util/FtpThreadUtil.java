package com.xlauncher.ics.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/12/24 0024
 * @Desc :多线程执行FTP上传图片
 **/
@Component
public class FtpThreadUtil {
    private final FtpUtil ftpUtil;
    private final PushEventUtil pushEventUtil;
    private static Logger logger = Logger.getLogger(FtpThreadUtil.class);

    @Autowired
    public FtpThreadUtil(FtpUtil ftpUtil, PushEventUtil pushEventUtil) {
        this.ftpUtil = ftpUtil;
        this.pushEventUtil = pushEventUtil;
    }

    /**
     * FTP上传图片线程
     *
     * @param imgName
     * @param img
     * @throws InterruptedException
     * @throws IOException
     */
    @Async
    void doTask(String imgName, byte[] img) throws InterruptedException, IOException {
        boolean result = ftpUtil.upload(imgName, img);
        logger.info("FtpThreadUtil启动线程执行上传图片! result." + result);
    }

    /**
     * 推送告警线程
     *
     * @param map
     * @throws InterruptedException
     * @throws IOException
     */
    @Async
    void doPushEvent(Map<String, Object> map) throws InterruptedException, IOException {
        int result = pushEventUtil.pushEvent(map);
        logger.info("ThreadUtil启动线程执行推送告警! result." + result);
    }
}
