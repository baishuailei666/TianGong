package com.xlauncher.es;

import com.thoughtworks.xstream.XStream;
import com.xlauncher.entity.Customer;
import com.xlauncher.entity.ReturnMessage;
import com.xlauncher.entity.User;
import com.xlauncher.entity.ValidBean;

import java.util.*;

public class XML2BeanTest {
    public static void main(String[] args) {
//        System.out.println(bean2Xml(createValidBean()));
//        xml2Bean();
        /*String xmlStr = "<Root>\n" +
                "    <valid>true</valid>\n" +
                "    <retMsg>  \n" +
                "        <resCode>0000</resCode>\n" +
                "        <resMessage>orderId:40288123623fdb8d01624c49f4b5000d</resMessage>\n" +
                "    </retMsg>\n" +
                "</Root>";
        Map<String, Class> map = new HashMap<>();
        map.put("Root", ValidBean.class);
        map.put("retMsg", ReturnMessage.class);
        ValidBean validBean = xml2Bean(xmlStr, map);
        System.out.println(validBean);*/
        bean2XmlUser();
    }

    public static void xml2Bean() {
        String xml = "<Root>\n" +
                "    <valid>true</valid>\n" +
                "    <retMsg>  \n" +
                "        <resCode>0000</resCode>\n" +
                "        <resMessage>orderId:40288123623fdb8d01624c49f4b5000d</resMessage>\n" +
                "    </retMsg>\n" +
                "</Root>";
        XStream xStream = new XStream();
        xStream.alias("Root", ValidBean.class);
        xStream.alias("retMsg", ReturnMessage.class);
        ValidBean validBean = (ValidBean) xStream.fromXML(xml);
        System.out.println(validBean);;
    }

    public static ValidBean xml2Bean(String xmlStr, Map<String, Class> aliasMap) {
        XStream xStream = new XStream();
        /*xStream.alias("Root", ValidBean.class);
        xStream.alias("retMsg", ReturnMessage.class);*/
        for (Map.Entry<String, Class> entry : aliasMap.entrySet()) {
            xStream.alias(entry.getKey(), entry.getValue());
        }
        ValidBean validBean = (ValidBean) xStream.fromXML(xmlStr);
        return validBean;
    }

    public static void bean2XmlUser() {
        User user = new User();
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        customer1.setCommodity("商品1");
        customer2.setCommodity("商品2");
        List<Customer> customerList = new ArrayList<Customer>();
        customerList.add(customer1);
        customerList.add(customer2);
        user.setName("beyond Li");
        user.setAge(23);
        user.setCustomerList(customerList);

        XStream xStream = new XStream();
        xStream.alias("User", User.class);
        xStream.alias("Customer", Customer.class);
        String s = xStream.toXML(user);
        System.out.println(s);
    }

    public static void bean2Xml() {
        ValidBean validBean = new ValidBean();
        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.setResCode("0000");
        returnMessage.setResMessage("orderId:40288123623fdb8d01624c49f4b5000d");
        validBean.setValid(true);
        validBean.setRetMsg(returnMessage);

        XStream xStream = new XStream();
        xStream.alias("Root", ValidBean.class);
        xStream.alias("retMsg", ReturnMessage.class);
        String s = xStream.toXML(validBean);
        System.out.println(s);
    }

    public static String bean2Xml(ValidBean validBean) {
        XStream xStream = new XStream();
        xStream.alias("Root", ValidBean.class);
        xStream.alias("retMsg", ReturnMessage.class);
        String s = xStream.toXML(validBean);
        return s;
    }

    public static ValidBean createValidBean() {
        ValidBean validBean = new ValidBean();
        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.setResCode("0000");
        returnMessage.setResMessage("orderId:40288123623fdb8d01624c49f4b5000d");
        validBean.setValid(true);
        validBean.setRetMsg(returnMessage);
        return validBean;
    }
}
