package com.xlauncher.utils;

import com.xlauncher.entity.AlertEvent;
import com.xlauncher.entity.EventDto;
import com.xlauncher.entity.ValidBean;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.log4j.Logger;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WSDLUtil {

    private static Logger logger = Logger.getLogger(WSDLUtil.class);
    /**
     * 获取调用接口实例化的bean
     * @param alertEvent
     * @return 接口返回结果实例化的ValidBean
     * @throws AxisFault 调用接口异常，可能是EPR异常或者IP无权限
     */
    public static ValidBean getValidBean(AlertEvent alertEvent) throws AxisFault {
        String url = "http://api.sunnyintec.com:7001/uip/services/NetmsServerSoap?wsdl";
        //使用RPC方式调用WebService
        RPCServiceClient serviceClient = new RPCServiceClient();
        //指定调用WebService的URL
        EndpointReference targetEPR = new EndpointReference(url);
        Options options = serviceClient.getOptions();
        //确定目标服务地址
        options.setTo(targetEPR);
        //确定调用方法
        options.setAction("createOrder");
        //设定头部信息，添加命名空间、用户、密码
        addValidation(serviceClient,
                "http://org.sunnyintec.com/",
                "xlauncher",
                "xlauncher.io");
        /**
         * 指定要调用的方法及WSDL文件的命名空间
         * 如果webservice服务端有axis2编写
         * 命名空间不一致导致的问题
         * org.apache.axis2.AxisFault: java.lang.RuntimeException: Unexpected subelement arg0
         */
        QName qname = new QName("http://org.sunnyintec.com/",
                "createOrder");
        //指定需要传输的对象
        EventDto ee = createEventDto(alertEvent);
        //指定queryWeather方法的参数值
        Object[] parameters = new Object[]{ee};
        //指定queryWeather方法返回值的数据类型的Class对象
        Class[] returnTypes = new Class[]{String.class};
        /*
        调用方法一 传递参数，调用服务，获取服务返回结果集
        OMElement element = serviceClient.invokeBlocking(qname, parameters);
        值得注意的是，返回结果就是一段由OMElement对象封装的xml字符串。
        我们可以对峙灵活应用，下面我们取第一个元素值，并打印。因为调用的方法返回一个结果
        String result = element.getFirstElement().getText();
        System.out.println(result);*/

        /**
         * 调用方法二
         * invokeBlocking方法有三个参数，其中：
         * 第一个参数的类型是QName对象，表示要调用的方法名；
         * 第二个参数表示WebService方法的参数值，参数类型为Object[]；
         * 第三个参数表示WebService方法的返回值类型Class对象，参数类型为Class[];
         * 当方法没有参数是，invokeBlocking方法的第二个参数值不能是null，而要使用
         * new Object[] {}
         * 如果被调用的WebService方法没有返回值，应使用RPCServiceClient类的invokeRobust方法
         * 该方法只有两个参数，他们的含义与invokeBlocking方法的前两个参数的含义相同
         */
        Object[] response = serviceClient.invokeBlocking(qname, parameters, returnTypes);
        String r = (String) response[0];
        ValidBean validBean = XMLBeanUtil.xml2Bean(r, XMLBeanUtil.generateAliasMapForSunTech());
        return validBean;
    }

    /**
     * 为SOAP Header构造验证信息
     * 如果服务端没有验证，那么不用在在Header中增加验证信息
     *
     * @param serviceClient RPCServiceClient对象，用于指定WebServiceURL，调用指定WebService的方法并返回结果
     * @param tns           TargetNameSpace 目标命名空间
     * @param user          用户名
     * @param password      密码
     */
    public static void addValidation(
            ServiceClient serviceClient, String tns, String user, String password) {
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace omNS = factory.createOMNamespace(tns, "nsl");
        OMElement header = factory.createOMElement("AuthenticationToken", omNS);
        OMElement omeUser = factory.createOMElement("Username", omNS);
        OMElement omePassword = factory.createOMElement("Password", omNS);
        omeUser.setText(user);
        omePassword.setText(password);
        header.addChild(omeUser);
        header.addChild(omePassword);
        serviceClient.addHeader(header);
    }

    /**
     * 创建EventDto对象
     * @return
     */
    public static EventDto createEventDto(AlertEvent alertEvent) {
        EventDto ee = new EventDto();
        ee.setType("33");
        switch (alertEvent.getTypeId()) {
            case 1:
                ee.setTypeDtl("3301");
                break;
            case 2:
                ee.setTypeDtl("3302");
                break;
            case 3:
                ee.setTypeDtl("3303");
                break;
            default:
                logger.error("错误的告警事件类型编号");
                break;
        }
        ee.setComponentId("");
        ee.setOccurPlace("密云区");
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(alertEvent.getEventStartTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            ee.setOccurDate(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("进行时间格式转换的时候出错");
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            ee.setOccurDate(calendar);
        }
        ee.setReporter("xlauncher");
        ee.setGridCode("1");
        ee.setGridName("密云区");
        try {
            String imgData = "SUNNYINTEC---test.jpg---" + Base64ImgUtil.getImgBase64StrByBytes(ImgUtil.getImgDataFromSource(alertEvent.getEventSource()));
            String[] imageStrArray = new String[1];
            imageStrArray[0] = imgData;
            ee.setImage1(imageStrArray);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("加载图片资源编码错误");
        }
        ee.setSubject(alertEvent.getTypeDescription());
        ee.setDescription("发现" + alertEvent.getTypeDescription());
        ee.setRefEntity("Channel");
        ee.setRefId(alertEvent.getChannelId());

        /*
         originStr:
         description发现船只非法侵入gridCode1gridName密云区occurDate2018/03/16 15:25:08occurPlace密云区reporterxlaunchersubject船只非法侵入type33typeDtl3301aaaaaa
         */
        String originStr = MessageFormat.format(
                "description{0}gridCode{1}gridName{2}occurDate{3}occurPlace{4}reporter{5}subject{6}type{7}typeDtl{8}aaaaaa",
                ee.getDescription(),
                ee.getGridCode(),
                ee.getGridName(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ee.getOccurDate().getTime()),
                ee.getOccurPlace(),
                ee.getReporter(),
                ee.getSubject(),
                ee.getType(),
                ee.getTypeDtl());
        ee.setSign(MD5Util.MD5(originStr));
        ///调试留用
        /*System.out.println(ee);
        System.out.println("MD5: " + MD5Util.MD5(originStr));
        System.out.println("demo MD5: " + MD5Util.MD5("description发现非法钓鱼gridCode1gridName密云区occurDate2018-03-19 13:52:08occurPlace密云区reporterxlaunchersubject非法钓鱼type33typeDtl3302aaaaaa"));
        */
        return ee;
    }
}
