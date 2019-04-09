package com.xlauncher.ics.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

/**
 * @author :baisl
 * @Email :baishuailei@xlauncher.io
 * @Date :2018/12/24 0024
 * @Desc :属性文件获得模型文件路径
 **/
@Component
public class PropertiesUtil {

    /**
     * 读写properties文件的对象
     */
    private Properties properties;

    private String fileName;
    private static Logger logger = Logger.getLogger(PropertiesUtil.class);

    /**
     * 初始化构造函数
     */
    public PropertiesUtil() {
        properties = new Properties();
        this.fileName = checkIfExist();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8")
            );
            properties.load(br);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断model.properties在target/classes下是否存在,如果不存在则创建文件
     *
     * @return 返回文件名（包含路径）
     */
    private String checkIfExist() {
        String fileName = PropertiesUtil.class.getClassLoader().getResource("").getPath() + "service.properties";
        logger.info("[service.properties]: " + fileName);
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

    /**
     * 写入一对properties信息（主键及其键值）
     * 如果主键已经存在，则更新该主键的值
     * 如果主键不存在，则插入一对键值
     *
     * @param keyName  键名
     * @param keyValue  键值
     */
    public void writeProperties(String keyName,String keyValue){
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8"));
            properties.setProperty(keyName, keyValue);
            properties.store(bw , "Write'"+keyName+"'value");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  得到资源路径
     *
     *  @return String
     */
    public String getPath(String key) {
        String path = PropertiesUtil.class.getClassLoader().getResource("").getPath() + key;
        logger.info("模型文件" + key +".path: " + path);
        return path;
    }
}
