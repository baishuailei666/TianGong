package com.xlauncher.util;

/**
 * 常量工具类：提供各服务的地址
 * @author YangDengcheng
 * @date 2018/1/24 17:01
 */

public class ConstantClassUtil {

   private static PropertiesUtil propertiesUtil = new PropertiesUtil();

    //CMS服务地址
    public final static String CMS_LOCATION = "http://" + propertiesUtil.readValue("cmsIp") + ":" + PropertiesUtil.readValue("cmsPort");


    //ICS服务地址
    public final static String ICS_LOCATION = "http://" + propertiesUtil.readValue("icsIp") + ":" + propertiesUtil.readValue("icsPort");

    //Kubernetes deployment服务地址(注意与replicaset的服务地址进行区分)
    public final static String K8S_DEPLOYMENT_LOCATION = "http://" + propertiesUtil.readValue("k8sIp") + ":" + propertiesUtil.readValue("k8sPort") + "/apis/extensions/v1beta1/namespaces/";

    //Kubernetes replicaset服务地址(注意与deployment的服务地址进行区分)
    public final static String K8S_REPLICASET_LOCATION = "http://" + propertiesUtil.readValue("k8sIp") + ":" + propertiesUtil.readValue("k8sPort") + "/apis/apps/v1beta1/namespaces/";

    //Kubernetes中命名空间
    public final static String K8S_NAMESPACE = propertiesUtil.readValue("k8sNamespace");

    //Kubernetes中镜像地址
    public final static String K8S_IMAGELOCATION = propertiesUtil.readValue("k8sImagelocation");

    //Kubernetes中ConfigMap的地址
    public final static String K8S_CONFIGMAP = "http://" + propertiesUtil.readValue("k8sIp") + ":" + propertiesUtil.readValue("k8sPort") + "/api/v1/namespaces/" + propertiesUtil.readValue("k8sNamespace") + "/configmaps";

    //DIM的IP
    public final static String DIM_IP = propertiesUtil.readValue("dimIp");

    //DIM的port
    public final static String DIM_PORT = propertiesUtil.readValue("dimPort");

    //ICS的IP
    public final static String ICS_IP = propertiesUtil.readValue("icsIp");

    //ICS的port
    public final static String ICS_PORT = propertiesUtil.readValue("icsPort");

    //RabbitMQ的IP
    public final static String MQ_IP = propertiesUtil.readValue("mqIp");

    //RabbitMQ的port
    public final static String MQ_PORT = propertiesUtil.readValue("mqPort");
}
