package com.xlauncher.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

/**
 * 读取配置文件工具类
 * @author YangDengcheng
 * @date 2018/1/22 10:09
 */

@Component
public class PropertiesUtil {

    /**
     * 读写properties文件的对象
     */
    private static Properties properties;
    /**
     * 文件名
     */
    private String fileName;

    /**
     * 初始化构造函数
     */
    public PropertiesUtil() {
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
     * service.properties在target/classes下是否存在,如果不存在则创建文件
     * @return 返回文件名（包含路径）
     */
    private String checkIfExist() {
        String fileName = PropertiesUtil.class.getClassLoader().getResource("").getPath() + "service.properties";
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
     * 读取属性文件中相应的键值
     *
     * @param key 键名称
     * @return String
     */
    public static String getKeyValue(String key){
        return properties.getProperty(key);
    }

    /**
     * 根据主键的key读取主键的值value
     *
     * @param key 键名称
     * @return 对应键的值
     */
    public static String readValue(String key){
        return properties.getProperty(key);
    }

    /**
     * 更新（或插入）一对properties信息（主键及其键值）
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
            properties.setProperty(keyName,keyValue);
            properties.store(bw , "Write'"+keyName+"'value");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新（或插入）一对properties信息（主键及其键值）
     * 如果主键已经存在，则更新该主键的值
     * 如果主键不存在，则插入一对键值
     *
     * @param keyName  键名
     * @param valueIP  键值
     * @param valuePort
     */
    public void updateProperties(String keyName,String valueIP,String valuePort){
        BufferedWriter bw;
        properties.setProperty(keyName + "Ip", valueIP);
        properties.setProperty(keyName + "Port", valuePort);
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8"));
            properties.store(bw , "Update'"+keyName+"'value" + valueIP + valuePort);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteProperties(String key){
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8"));
            properties.remove(key);
            properties.store(bw , "Delete'"+key+"'value");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
