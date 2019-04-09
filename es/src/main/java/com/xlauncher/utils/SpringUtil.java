//package com.xlauncher.utils;
//
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
///**
// * 普通类调用Spring bean对象
// * 说明：
// * 此类需要放在App.java同包或者子包下才能被扫描，否则失败。
// * @author baishuailei
// * @date 2018-07-25
// */
//
//public class SpringUtil implements ApplicationContextAware{
//    private static ApplicationContext applicationContext = null;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        if (SpringUtil.applicationContext == null) {
//            SpringUtil.applicationContext = applicationContext;
//        }
//        System.out.println("ApplicationContext配置成功，在普通类可以通过调用SpringUtil.getAppContext()获取applicationContext对象");
//        System.out.println("applicationContext:" + SpringUtil.applicationContext);
//    }
//
//    /**
//     * 获取applicationContext
//     * @return
//     */
//    public static ApplicationContext getApplicationContext() {
//        return applicationContext;
//    }
//
//    /**
//     * 通过name获取 bean
//     * @param name
//     * @return
//     */
//    public static Object getBean(String name) {
//        return getApplicationContext().getBean(name);
//    }
//
//    /**
//     * 通过class获取 bean
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public static <T> T getBean(Class<T> clazz) {
//        return getApplicationContext().getBean(clazz);
//    }
//
//    /**
//     * 通过name以及class返回指定的 bean
//     * @param name
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public static <T> T getBean(String name, Class<T> clazz) {
//        return getApplicationContext().getBean(name, clazz);
//    }
//}
