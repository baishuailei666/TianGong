package com.xlauncher.util.synsunnyitec;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

/**
 * 第三方数据（device、channel、org）同步 时间可配置
 * @date 2018-05-21
 * @author 白帅雷
 */
@Component
public class SynSunnyintecProperties {
    /**
     * 读写properties文件的对象
     */
    private static Properties properties;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 添加一个记录器
     */
    private static Logger logger = Logger.getLogger(SynSunnyintecProperties.class);

    /**
     * 初始化构造函数
     */
    public SynSunnyintecProperties() {
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

    /**
     * 判断logConfiguration.properties在target/classes下是否存在,如果不存在则创建文件
     * @return 返回文件名（包含路径）
     */
    private String checkIfExist() {
        String fileName = SynSunnyintecProperties.class.getClassLoader().getResource("").getPath() + "synConf.properties";
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                logger.info("创建文件:" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }
    /**
     * 第三方同步时间
     * @return ftp服务器用户名
     */
    public String synTime() {
        return properties.getProperty("synTime","10");
    }

    /**
     * 第三方同步account
     * @return ftp服务器用户名
     */
    public String synAccount() {
        return properties.getProperty("synAccount","launcher01");
    }
    /**
     * 第三方同步password
     * @return ftp服务器用户名
     */
    public String synPassword() {
        return properties.getProperty("synPassword","123456");
    }

    /**
     * 获得当天0点时间毫秒数
     * @return
     */
    private long getDefaultStartTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 获得当天24点时间毫秒数
     * @return
     */
    private long getDefaultEndTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
}
