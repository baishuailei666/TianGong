package com.xlauncher.utils;

import com.thoughtworks.xstream.XStream;
import com.xlauncher.entity.ReturnMessage;
import com.xlauncher.entity.ValidBean;

import java.util.HashMap;
import java.util.Map;

/**
 * XML与Bean相互转换的工具
 * @author 张霄龙
 * @since 2018-03-22
 */
public class XMLBeanUtil {

    /**
     * 将java bean转换为xml字符串形式
     * @param validBean 需要转换的java bean对象
     * @param aliasMap java bean类名与XML中标签的映射关系
     * @return xml形式的字符串
     */
    public static String bean2Xml(ValidBean validBean, Map<String, Class> aliasMap) {
        XStream xStream = new XStream();
        for (Map.Entry<String, Class> entry : aliasMap.entrySet()) {
            xStream.alias(entry.getKey(), entry.getValue());
        }
        String s = xStream.toXML(validBean);
        return s;
    }

    /**
     * 将xml字符串转换为java bean对象
     * @param xmlStr 需要转换的xml字符串
     * @param aliasMap java Bean类名称和XML文件中标签字段的对应关系
     * @return 转换后的java bean对象
     */
    public static ValidBean xml2Bean(String xmlStr, Map<String, Class> aliasMap) {
        XStream xStream = new XStream();
        for (Map.Entry<String, Class> entry : aliasMap.entrySet()) {
            xStream.alias(entry.getKey(), entry.getValue());
        }
        ValidBean validBean = (ValidBean) xStream.fromXML(xmlStr);
        return validBean;
    }

    /**
     * 生成装换需要用到的Java bean类名和XML标签的映射关系
     * @return
     */
    public static Map<String, Class> generateAliasMapForSunTech() {
        Map<String, Class> aliasMap = new HashMap<>(2);
        aliasMap.put("Root", ValidBean.class);
        aliasMap.put("retMsg", ReturnMessage.class);
        return aliasMap;
    }
}
