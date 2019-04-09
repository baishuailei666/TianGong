package com.xlauncher.es;

import com.xlauncher.utils.MD5Util;

public class MD5UtilTest {
    public static void main(String[] args) {
        String originStr =
                "description发现非法钓鱼gridCode1gridName密云区occurDate2018-03-19 13:52:08occurPlace密云区reporterxlaunchersubject非法钓鱼type33typeDtl3302aaaaaa";
        String md5Str = MD5Util.MD5(originStr);
        System.out.println(md5Str);
    }
}
