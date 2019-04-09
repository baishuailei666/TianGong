package com.xlauncher.util;

import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Init
 * @author 叶茂
 * @date 2018-06-11
 */
@Component
public class Init implements ServletContextListener {

    private static Properties properties;
    private String fileName;
    public Init() {
        properties = new Properties();
        this.fileName = checkIfExist();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
            properties.load(br);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        checkIfExist();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            properties.setProperty("Time",DatetimeUtil.getDate(System.currentTimeMillis()));
            properties.store(fileOutputStream,DatetimeUtil.getDate(System.currentTimeMillis()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}

    private String checkIfExist() {
        String fileName = Init.class.getClassLoader().getResource("").getPath() + "init.properties";
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    public String getInitime(){

        try {
            InputStream in = new BufferedInputStream(new FileInputStream(fileName));
            properties.load(in);
            Enumeration en = properties.propertyNames(); //得到配置文件的名字

             while(en.hasMoreElements()) {
                     String strKey = (String) en.nextElement();
                     String strValue = properties.getProperty(strKey);
                 }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("Time", DatetimeUtil.getDate((long) (System.currentTimeMillis() - Math.random()*1000)));
    }

    public List runTime() throws ParseException {
        String initTime = this.getInitime();
        String momentTime = DatetimeUtil.getDate(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long mih = (sdf.parse(momentTime).getTime()-sdf.parse(initTime).getTime())/1000/60/60;
        long mim = (sdf.parse(momentTime).getTime()-sdf.parse(initTime).getTime()-mih*1000*60*60)/1000/60;
        long mis = (sdf.parse(momentTime).getTime()-sdf.parse(initTime).getTime()-mih*1000*60*60-mim*1000*60)/1000;
        List list = new ArrayList();
        list.add(mih);
        list.add(mim);
        list.add(mis);
        return list;
    }
}