package com.xlauncher.util;

import java.util.UUID;

/**
 * 初始化32位编号
 * @author mao ye
 * @since 2018-02-01
 */
public class Initialise {
    public static String initialise(){
        String id = UUID.randomUUID().toString();
        id=id.replace("-","");
        return id;
    }
}