package com.xlauncher.util.alertlog;

import com.xlauncher.entity.AlertLog;
import com.xlauncher.service.AlertLogService;
import com.xlauncher.util.DatetimeUtil;
import com.xlauncher.util.FileSortUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 告警日志
 * @date 2018-05-14
 * @author 白帅雷
 */
public class LogRunner implements Runnable {

    private ObjectInputStream ois;
    private static int count = 0;
    private AlertLogService alertLogService = (AlertLogService) new FileSystemXmlApplicationContext("classpath:applicationContext.xml").getBean("alertLogService");
    private ApplicationContext applicationContext;
    private Logger logger = Logger.getLogger(LogRunner.class);
    /**
     * 构造函数
     */
    public LogRunner(Socket client) {
        try {
            if (null != client && !client.isClosed()) {
                logger.info("LogRunner-socket连接正常：" + client.getInputStream());
                this.ois = new ObjectInputStream(client.getInputStream());
                this.applicationContext = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
                this.alertLogService = (AlertLogService) this.applicationContext.getBean("alertLogService");
            } else {
                logger.warn("LogRunner-socket连接关闭");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                LoggingEvent loggingEvent = (LoggingEvent) this.ois.readObject();
                System.out.println(Integer.toString(count++) + ": " + loggingEvent.getLoggerName() + ":" + loggingEvent.getMessage());
                //告警日志登记priority
                String priority = loggingEvent.getLevel().toString();
                //告警日志发生的时间time
                String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(loggingEvent.getTimeStamp()));
                //输出应用程序启动到输出日志销号的毫秒数time_span
                int timeSpan =(int)(loggingEvent.getTimeStamp()- LoggingEvent.getStartTime());
                //输出对应的线程名称thread
                String thread = loggingEvent.getThreadName();
                //错误抛出的行line_number
                String lineNumber = loggingEvent.getLocationInformation().getLineNumber();
                //告警抛出的文件名file_name
                String fullFileName= loggingEvent.getLocationInformation().getFileName();
                String[] fileNameLst = fullFileName.split("\\.");
                String fileName = fileNameLst[0];
                //告警抛出的类名class_name
                String fullClassName = loggingEvent.getLocationInformation().getClassName();
                //告警抛出的方法名称method_name
                String methodName = loggingEvent.getLocationInformation().getMethodName();
                //告警抛出自带的信息说明message
                String message = loggingEvent.getMessage().toString();
                System.out.println("priority:" + priority);
                System.out.println("time:" + time);
                System.out.println("timeSpan:" + timeSpan);
                System.out.println("thread: " + thread);
                System.out.println("lineNumber:" + lineNumber);
                System.out.println("fileName: " + fileName);
                System.out.println("className: " + fullClassName);
                System.out.println("methodName: " + methodName);
                System.out.println("message: " + message);
                System.out.println("----*----*----*----*----*----*----*----*----*----*----*----");
                AlertLog alertLog = new AlertLog();
                alertLog.setAlertPriority(priority);
                alertLog.setAlertTime(time);
                alertLog.setAlertTimeSpan(timeSpan);
                alertLog.setAlertThread(thread);
                alertLog.setAlertLineNum(lineNumber);
                alertLog.setAlertFileName(fileName);
                alertLog.setAlertClassName(fullClassName);
                alertLog.setAlertMethodName(methodName);
                alertLog.setAlertMessage(message);
                switch (priority){
                    case "WARN": alertLog.setAlertType("警告");
                        break;
                    case "ERROR": alertLog.setAlertType("错误");
                        break;
                    case "FATAL": alertLog.setAlertType("致命");

                        break;
                    default: break;
                }
                String[] pri = {"WARN", "ERROR", "FATAL"};
                if (Arrays.asList(pri).contains(priority)) {
                    System.out.println("添加告警日志数据库：" + alertLog);
                    this.alertLogService.insertAlertLog(alertLog);
                }
//              FileSortUtil.saveLogFile("/var/log/tiangong/logs/" + DatetimeUtil.getDate(DatetimeUtil.getDate(System.currentTimeMillis())) + "告警日志信息.txt", alertLog.toString(), 50 * 1024 * 1024);
            }
        } catch (RuntimeException e) {
           e.printStackTrace();
            logger.error("LogRunner-RuntimeException: " + e.getMessage() + e.getCause() + e.getLocalizedMessage());
        } catch (Exception e) {
            logger.error("LogRunner-Socket Exception: " + e.getMessage() + e.getCause() + e.getLocalizedMessage());
        }
    }
}


