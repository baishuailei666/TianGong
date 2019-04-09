package com.xlauncher.service.impl;

import com.xlauncher.dao.dimdao.IChannelDao;
import com.xlauncher.dao.dimdao.IDeviceDao;
import com.xlauncher.entity.Channel;
import com.xlauncher.entity.configmap.Data;
import com.xlauncher.entity.configmap.MetaDataInConfigMap;
import com.xlauncher.entity.deployment.*;
import com.xlauncher.entity.deployment.spec.*;
import com.xlauncher.service.*;
import com.xlauncher.util.ConstantClassUtil;
import com.xlauncher.util.ThreadUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YangDengcheng
 * @date 2018/2/27 15:38
 */
@Service("channelService")
public class ChannelServiceImpl implements ChannelService {
    private static final Logger LOGGER = Logger.getLogger(ChannelServiceImpl.class);

    public static ThreadGroup tg = new ThreadGroup("channelGroup");

    {
        tg = Thread.currentThread().getThreadGroup();
    }

    @Autowired
    private IChannelDao iChannelDao;
    @Autowired
    CreateModelService createModelService;
    @Autowired
    GetModelService getModelService;
    @Autowired
    DeleteModelService deleteModelService;
    @Autowired
    UpdateModelService updateModelService;
    @Autowired
    PushStreamService pushStreamService;

    @Override
    public List<Channel> queryAllChannel() {
        return iChannelDao.queryAllChannel();
    }

    @Override
    public int insertChannel(Channel channel) {

        //K8S的apiServer
        String deploymentUrl = ConstantClassUtil.K8S_DEPLOYMENT_LOCATION + ConstantClassUtil.K8S_NAMESPACE + "/deployments";
        String configMapUrl = ConstantClassUtil.K8S_CONFIGMAP;

        //新增channel至数据库
        int result = iChannelDao.insertChannel(channel);

        //创建ConfigMap设置所需的参数
        com.xlauncher.entity.configmap.ConfigMap configMapObj = new com.xlauncher.entity.configmap.ConfigMap();
        Data data = new Data();
        MetaDataInConfigMap metaDataInConfigMap = new MetaDataInConfigMap();

        //设置ConfigMap的参数
        data.setProperties(iChannelDao.queryChannelMsgWithDevice(channel.getChannelId()).toConvertProperties());              //TODO 填入需要的参数
        metaDataInConfigMap.setName("channel-" + channel.getChannelId());
        metaDataInConfigMap.setNamespace(ConstantClassUtil.K8S_NAMESPACE);
        configMapObj.setData(data);
        configMapObj.setMetadata(metaDataInConfigMap);

        createModelService.createConfigMap(configMapUrl,configMapObj);


        //创建Deployment设置所需的参数
        Deployment deployment = new com.xlauncher.entity.deployment.Deployment();
        Labels labels = new Labels();
        Metadata metadataOut = new Metadata();
        MatchLabels matchLabels = new MatchLabels();
        Selector selector = new Selector();
        Spec specOut = new Spec();
        Template template = new Template();
        com.xlauncher.entity.deployment.spec.MetadataIn metadataIn = new com.xlauncher.entity.deployment.spec.MetadataIn();
        com.xlauncher.entity.deployment.spec.SpecIn specIn = new com.xlauncher.entity.deployment.spec.SpecIn();
        Containers containers = new Containers();
        NodeSelector nodeSelector = new NodeSelector();
        Requests requests = new Requests();
        Resources resources = new Resources();
        VolumeMounts volumeMounts = new VolumeMounts();
        Volumes volumes = new Volumes();
        ConfigMapIn configMapIn = new ConfigMapIn();


        //设置外部metadata的值
        labels.setApp("channel-" + channel.getChannelId());                    //设置标签名称
        metadataOut.setName("channel-" + channel.getChannelId());
        metadataOut.setNamespace(ConstantClassUtil.K8S_NAMESPACE);             //设置命名空间
        metadataOut.setLabels(labels);

        //设置外部spec的参数值
        matchLabels.setApp("channel-" + channel.getChannelId());
        selector.setMatchLabels(matchLabels);

        //设置内部metadata的参数值
        Map<String,String> labelMap = new HashMap<>();
        labelMap.put("app","channel-" + channel.getChannelId());
        metadataIn.setLabels(labelMap);

        //设置内部spec的参数值
        requests.setCpu("1024m");                                               //设置CPU
        requests.setMemory("512Mi");                                            //设置Memory
        resources.setRequests(requests);
        containers.setImage(ConstantClassUtil.K8S_IMAGELOCATION);               //设置镜像地址
        containers.setName("channel-" + channel.getChannelId());                //设置容器的名称
        containers.setResources(resources);

        //设置volumeMounts
        volumeMounts.setName("config-volume1");
        volumeMounts.setMountPath("/config");
        List<VolumeMounts> volumeMountsList = new ArrayList<>();
        volumeMountsList.add(volumeMounts);
        containers.setVolumeMounts(volumeMountsList);


        //设置容器内容
        List<Containers> containersList = new ArrayList<>();
        containersList.add(containers);

        nodeSelector.setApolloNamespace(ConstantClassUtil.K8S_NAMESPACE);       //设置nodeSelector的值

        //设置configMap的参数以及volumes参数
        configMapIn.setDefaultMode(123);
        configMapIn.setName("channel-" + channel.getChannelId());
        volumes.setConfigMap(configMapIn);
        volumes.setName("config-volume1");
        List<Volumes> volumesList = new ArrayList<>();
        volumesList.add(volumes);
        specIn.setNodeSelector(nodeSelector);
        specIn.setContainers(containersList);
        specIn.setVolumes(volumesList);

        template.setSpec(specIn);
        template.setMetadata(metadataIn);

        specOut.setReplicas(1);                                                 // 设置副本控制器数量
        specOut.setSelector(selector);
        specOut.setTemplate(template);

        deployment.setMetadata(metadataOut);
        deployment.setSpec(specOut);


        try {
            createModelService.createDeployment(deploymentUrl,deployment);     //创建Deployment
            LOGGER.info("Create deployment...");
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("Create deployment failed ... ");
        }




        return result;
    }

    /**
     * 更新通道信息
     *
     * @param channel
     * @return
     */
    @Override
    public int updateChannelMsg(Channel channel) {
        updateModelService.updateMsgToCMS(channel);
        return iChannelDao.updateChannelMsg(channel);
    }

    /**
     * 删除设备并且删除设备对应的Deployment与Replicaset
     *
     * @param id
     * @return Int success:1 failed:0
     */
    @Override
    public int deleteChannel(String id) {
        // 删除deployment
        String deploymentName = "channel-" + id;

        //replicaset的API获取
        String getReplicasetAPI = ConstantClassUtil.K8S_DEPLOYMENT_LOCATION + ConstantClassUtil.K8S_NAMESPACE+ "/replicasets?labelSelector=app=" + deploymentName;

        //查询deployment对应的replicaset名称
        String replicasetName = getModelService.getReplicaset(getReplicasetAPI).replace("_","-");

        //删除replicaset的API
        String deleteReplicasetAPI = ConstantClassUtil.K8S_DEPLOYMENT_LOCATION + ConstantClassUtil.K8S_NAMESPACE + "/replicasets/" + replicasetName + "?orphanDependents=false";

        //删除deployment的API
        String deleteDeploymentAPI = ConstantClassUtil.K8S_REPLICASET_LOCATION + ConstantClassUtil.K8S_NAMESPACE + "/deployments/" + deploymentName;

        //删除configMap的API
        String deleteConfigMapAPI = ConstantClassUtil.K8S_CONFIGMAP + "/channel-" + id;

        if (deploymentName != null && deleteDeploymentAPI != null && deleteReplicasetAPI != null && replicasetName != null && deleteConfigMapAPI != null){
            //执行删除deployment操作
            deleteModelService.deleteApplication(deleteDeploymentAPI);
            //执行删除replicaset操作
            deleteModelService.deleteApplication(deleteReplicasetAPI);
            //执行删除configmap操作
            deleteModelService.deleteApplication(deleteConfigMapAPI);
            //执行数据库删除操作
            return iChannelDao.deleteChannel(id);
        }else {
            return 0;
        }
    }

    /**
     * 激活通道
     *
     * @param channelId 通道编号
     * @return 数据库的操作情况
     */
    @Override
    public int activeChannel(String channelId) {
        int status;
        Channel channel = this.iChannelDao.queryChannelMsg(channelId);
        // 创建线程
        ThreadUtil threadUtil = new ThreadUtil(channel);
        Thread thread = new Thread(tg,threadUtil,channel.getChannelId());

        // 获取线程ID并存入数据库
        long threadId = thread.getId();
        // 新增设备到数据库
        status = this.iChannelDao.activeChannel(channelId,(int)threadId);
        if (status == 1) {
            LOGGER.info("激活通道成功！channelId：" + channelId);
        } else {
            LOGGER.info("激活通道失败！channelId：" + channelId);
        }

        thread.start();
        LOGGER.info("激活通道！并创建线程唤醒设备" + thread);

        return status;
    }

    /**
     * 停用通道
     *
     * @param channelId 通道编号
     * @return 数据库的操作情况
     */
    @Override
    public int disableChannel(String channelId) {
        int status;
        // 中断通信
        int channelThreadId = iChannelDao.queryChannelThread(channelId);
        interruptThread(channelThreadId);
        LOGGER.info("停用通道 - 中断通信！");

        status = this.iChannelDao.disableChannel(channelId);
        if (status == 1) {
            LOGGER.info("停用通道成功！channelId：" + channelId);
        } else {
            LOGGER.info("停用通道失败！channelId：" + channelId);
        }
        return status;
    }

    /**
     * 激活设备以及对应通道disableDeviceChannel
     *
     * @param channelSourceId 通道资源编号
     * @return 数据库的操作情况
     */
    @Override
    public int activeDeviceChannel(String channelSourceId) {
        LOGGER.info("激活设备以及对应通道disableDeviceChannel, channelSourceId：" + channelSourceId);
        List<Channel> channelList = this.iChannelDao.queryChannelBySourceId(channelSourceId);
        final int[] status = {0};
        channelList.forEach(channel -> {
            // 创建线程
            ThreadUtil threadUtil = new ThreadUtil(channel);
            Thread thread = new Thread(tg,threadUtil,channel.getChannelId());
            // 获取线程ID并存入数据库
            long threadId = thread.getId();
            status[0] = this.iChannelDao.activeChannel(channel.getChannelId(),(int)threadId);
            LOGGER.info("激活设备以及对应通道 - 获取线程ID并存入数据库, threadId：" + threadId);

            thread.start();
            LOGGER.info("激活设备以及对应通道 - 创建线程唤醒设备, thread：" + thread);
        });
        return status[0];
    }

    /**
     * 停用设备以及对应通道disableDeviceChannel
     *
     * @param channelSourceId 通道资源编号
     * @return 数据库的操作情况
     */
    @Override
    public int disableDeviceChannel(String channelSourceId) {
        LOGGER.info("停用设备以及对应通道disableDeviceChannel, channelSourceId：" + channelSourceId);
        List<Channel> channelList = this.iChannelDao.queryChannelBySourceId(channelSourceId);
        channelList.forEach(channel -> {
            // 中断通信
            int channelThreadId = iChannelDao.queryChannelThread(channel.getChannelId());
            LOGGER.info("停用设备以及对应通道 - 中断通信！channelThreadId：" + channelThreadId);
            interruptThread(channelThreadId);
        });

        return this.iChannelDao.disableDeviceChannel(channelSourceId);
    }


    @Override
    public Channel queryChannelMsg(String id) {
        return iChannelDao.queryChannelMsg(id);
    }

    @Override
    public Channel queryChannelMsgWithDevice(String id) {
        return iChannelDao.queryChannelMsgWithDevice(id);
    }

    /**
     * 添加通道（创建线程建立通信）
     *
     * @param channel
     * @return
     */
    @Override
    public int insertChannelWithDIM(Channel channel) {
        LOGGER.info("[insertChannelWithDIM] - 添加通道" + channel);
        // 新增设备到数据库
        iChannelDao.insertChannel(channel);
        LOGGER.info("[insertChannelWithDIM] - 通道存入数据库");
        // 创建线程
        ThreadUtil threadUtil = new ThreadUtil(channel);
        Thread thread = new Thread(tg,threadUtil,channel.getChannelId());
        thread.start();
        LOGGER.info("[insertChannelWithDIM] - 创建线程" + thread);
        // 获取线程ID并存入数据库
        long threadId = thread.getId();
        channel.setChannelThreadId((int)threadId);
        LOGGER.info("[insertChannelWithDIM] - 获取线程ID并存入数据库:" + threadId);
        // 线程ID并存入数据库
        return iChannelDao.updateChannelThreadId(channel.getChannelThreadId(), channel.getChannelId());
    }

    /**
     * 更新通道（中断通信、创建线程、建立通信）
     *
     * @param channel
     * @return
     */
    @Override
    public int updateChannelWithDIM(Channel channel) {
        LOGGER.info("[updateChannelWithDIM] - 更新通道" + channel);
        String channelId = channel.getChannelId();
        int channelThreadId = iChannelDao.queryChannelThread(channelId);

        // 中断与CMS和ICS的通信
        interruptThread(channelThreadId);
        LOGGER.info("[updateChannelWithDIM] - 中断与CMS和ICS的通信" + channel);
        // 创建线程
        ThreadUtil threadUtil = new ThreadUtil(channel);
        Thread thread = new Thread(tg,threadUtil,channel.getChannelId());
        thread.start();
        LOGGER.info("[updateChannelWithDIM] - 创建线程" + thread);
        // 获取线程ID并存入数据库
        long threadId = thread.getId();
        channel.setChannelThreadId((int)threadId);
        LOGGER.info("[updateChannelWithDIM] - 获取线程ID并存入数据库" + threadId);
        return iChannelDao.updateChannelMsg(channel);
    }

    /**
     * 删除通道并中断与ICS和CMS的通信
     * @param id 通道id
     * @return success：1 failed：0
     */
    @Override
    public int deleteChannelWithDIM(String id) {
        int channelThreadId = iChannelDao.queryChannelThread(id);
        LOGGER.info("[deleteChannelWithDIM] - 删除通道并中断与ICS和CMS的通信" + id + channelThreadId);
        interruptThread(channelThreadId);
        return iChannelDao.deleteChannel(id);
    }

    /**
     * 中断线程（中断和ICS和CMS的通信）
     *
     * @param threadId 线程号
     */
    public void interruptThread(long threadId){
        Thread[] threads = new Thread[(int) (tg.activeCount())];
        LOGGER.info("[interruptThread] - 中断线程（中断和ICS和CMS的通信）" +threadId);
        int count = tg.enumerate(threads,true);
        for (int i=0;i<count;i++){
            if (threads[i].getId() == threadId){
                threads[i].interrupt();
            }
        }
    }
}
