package com.xlauncher.util.synsunnyitec;

import com.xlauncher.entity.EventAlert;
import com.xlauncher.util.SpringContextUtil;
import org.apache.log4j.Logger;
import java.util.Map;

/**
 * 第三方上报告警事件线程工具类
 * @author baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/9/14 0014
 * @Desc :
 **/
public class PushEventThread implements Runnable {

    /**
     * 通过SpirngUtil调用bean对象
     */
    private PushEventToSunnyintec pushEventToSunnyintec = (PushEventToSunnyintec) SpringContextUtil.getBean("pushEventToSunnyintec");

    public EventAlert eventAlert;
    private static Logger logger = Logger.getLogger(PushEventThread.class);

    /**
     * 初始化
     * @param eventAlert
     */
    public PushEventThread(EventAlert eventAlert) {
        this.eventAlert = eventAlert;
    }

    public void pushEvent() {
        while (!Thread.currentThread().isInterrupted()) {
            Map map = pushEventToSunnyintec.sunnyintecLogin();
            // 调用用户接口成功返回：code=200, ticket:请求接口需要的验证
            if (!map.get("code").equals(200)) {
                logger.warn("sunnyintecLogin error!" + map);
                continue;
            }
            String ticket = (String) map.get("ticket");
            logger.info(" PushEventThread pushEvent ticket [" + ticket + "]");
            Map submitEventMap = pushEventToSunnyintec.sunnyintecSubmitEvent(eventAlert, ticket);
            logger.info(" PushEventThread pushEvent eventAlert [" + eventAlert + "]");
            // 上报告警成功返回结果：code=0, id:上报成功事件id, eventnum:当前创建的事件编号
            if (submitEventMap.get("code").equals(0)) {
                logger.info("PushEventThread pushEvent sunnyintecSubmitEvent is successful!" + submitEventMap);
                // TODO

                return;
            }
            logger.warn("PushEventThread pushEvent sunnyintecSubmitEvent is failure!" + submitEventMap);

            try {
                logger.debug("上报线程睡眠等待：Thread.sleep(1000 * 30) ");
                Thread.sleep(1000 * 30);
            } catch (InterruptedException e) {
                logger.error("PushEventThread InterruptedException：" + e.getMessage() + e.getLocalizedMessage() + e.getCause());
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * @see Thread#run()
     */
    @Override
    public void run() {
        logger.info("上报告警, 启动线程PushEventThread pushEvent()");
        this.pushEvent();
    }
}
