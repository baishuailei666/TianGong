package com.xlauncher.util;

import org.springframework.stereotype.Component;

/**
 * 操作系统判断工具类
 * @author 白帅雷
 * @date 2018-06-20
 */
@Component
public class OsUtil {

    private static final String WINDOWS = "win";
    private static final String LINUX = "linux";

    /**
     * 判断当前操作系统，并返回日志存储的路径
     *
     * @return 日志存储的路径
     */
    String whichOsLog() {
        String os = System.getProperties().getProperty("os.name");
        os = os.toUpperCase();
        if (os.toLowerCase().startsWith(WINDOWS)) {
            return "D:\\tiangong\\logs\\";
        } else if (os.toLowerCase().startsWith(LINUX)) {
            return "/var/tmp/tiangong/logs/";
        } else {
            return "/var/tmp/tiangong/logs/";
        }
    }

    /**
     * 判断当前操作系统，并返回图片存储的路径
     *
     * @return 图片存储的路径
     */
      String whichOsImage() {
        String os = System.getProperties().getProperty("os.name");
        if (os.toLowerCase().startsWith(LINUX)) {
            return "/var/tmp/tiangong/images/";
        } else if (os.toLowerCase().startsWith(WINDOWS)) {
            return "D:\\tiangong\\images\\";
        } else {
            return "D:\\tiangong\\images\\";
        }
    }

    /**
     * 判断当前操作系统，并返回图片存储的路径
     *
     * @return 图片存储的路径
     */
    String whichOsMysql() {
        String os = System.getProperties().getProperty("os.name");
        if (os.toLowerCase().startsWith(LINUX)) {
            return "/usr/bin/";
        } else if (os.toLowerCase().startsWith(WINDOWS)) {
            return "C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\";
        } else {
            return "/usr/bin/";
        }
    }

}
