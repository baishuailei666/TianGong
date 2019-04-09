package com.xlauncher.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author baisl
 * @time 18-8-16 下午2:42
 */
@Component
public class NET_DVR_ERRORUtil {
    private static Properties properties;
    private String fileName;

    public NET_DVR_ERRORUtil() {
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

    private String checkIfExist() {
        String fileName = PropertiesUtil.class.getClassLoader().getResource("").getPath() + "NET_DVR_ERROR.properties";
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
     * 查询海康错误码获得错误说明信息
     *
     * @param key
     * @return
     */
    public String getNET_DVR_ERROR(String key){

        return properties.getProperty(key);
    }
}
