package com.xlauncher.util;

import com.xlauncher.dao.ComponentDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 组件监控工具类：获取各个组件的运行状态
 * @author 白帅雷
 * @date 2018-07-04
 */
@Component
public class HeartBeatUtil implements Runnable{
    @Autowired
    ComponentDao componentDao;
    private Logger logger = Logger.getLogger(HeartBeatUtil.class);
    /**
     * 利用复杂构造器可以实现超时设置，内部实际实现为 HttpClient
     */
    private RestTemplate restTemplate;

    /**
     * 复杂构造函数的使用，用于设置超时
     */
    private HeartBeatUtil(){
        // 复杂构造函数的使用
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // 设置超时时间 1秒
        requestFactory.setConnectTimeout(1000);
        requestFactory.setReadTimeout(1000);
        restTemplate = new RestTemplate(requestFactory);
    }


    /**
     * es运行状态
     * @return 200正常
     */
    public int getESStatus(){
        ResponseEntity<String> mapResponseEntity;
        try {
            //header设置
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("Content-Type", "application/json;charset=UTF-8");
            HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
            restTemplate.getMessageConverters().add(new HttpMessageConverter());
            String getUrl = "http://" + componentDao.getComponentByAbbr("es").getComponentIp() + ":" + componentDao.getComponentByAbbr("es").getComponentPort() + "/es/";
            logger.info("心跳监控-es运行状态, 请求url路径：" + getUrl);
            mapResponseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, httpEntity, String.class);
            if (mapResponseEntity != null) {
                return 1;
            } else {
                return 0;
            }
        } catch (RuntimeException e) {
            return 0;
        }
    }

    /**
     * dim运行状态
     * @return 200正常
     */
    public int getDIMStatus(){
        ResponseEntity<String> mapResponseEntity;
        try {
            //header设置
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("Content-Type", "application/json;charset=UTF-8");
            HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
            restTemplate.getMessageConverters().add(new HttpMessageConverter());
            String getUrl = "http://" + componentDao.getComponentByAbbr("dim").getComponentIp() + ":" + componentDao.getComponentByAbbr("dim").getComponentPort() + "/dim/";
            logger.info("心跳监控-dim运行状态, 请求url路径：" + getUrl);
            mapResponseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, httpEntity, String.class);
            if (mapResponseEntity != null) {
                return 1;
            } else {
                return 0;
            }
        } catch (RuntimeException e) {
            return 0;
        }
    }

    /**
     * ics运行状态
     * @return 200正常
     */
    public int getICSStatus(){
        ResponseEntity<String> mapResponseEntity;
        try {
            //header设置
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("Content-Type", "application/json;charset=UTF-8");
            HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
            restTemplate.getMessageConverters().add(new HttpMessageConverter());
            String[] ports = componentDao.getComponentByAbbr("ics").getComponentPort().split(",");
            String port = ports[0];
            String getUrl = "http://" + componentDao.getComponentByAbbr("ics").getComponentIp() + ":" + port + "/ics/heartbeat";
            logger.info("心跳监控-ics运行状态, 请求url路径：" + getUrl);
            mapResponseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, httpEntity, String.class);
            if (mapResponseEntity != null) {
                return 1;
            } else {
                return 0;
            }
        } catch (RuntimeException e) {
            return 0;
        }
    }

    /**
     * cms运行状态
     * @return 200正常
     */
    public int getCMSStatus(){
        ResponseEntity<String> mapResponseEntity;
        try {
            //header设置
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("Content-Type", "application/json;charset=UTF-8");
            HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
            restTemplate.getMessageConverters().add(new HttpMessageConverter());
            String getUrl = "http://" + componentDao.getComponentByAbbr("cms").getComponentIp() + ":" + componentDao.getComponentByAbbr("cms").getComponentPort() + "/cms/";
            logger.info("心跳监控-cms运行状态, 请求url路径：" + getUrl);
            mapResponseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, httpEntity, String.class);
            if (mapResponseEntity != null) {
                return 1;
            } else {
                return 0;
            }
        } catch (RuntimeException e) {
            return 0;
        }
    }

    /**
     * 组件监控，获取各个组件的状态信息
     * @return List<Object>
     */
    public List<Object> heartBeat(){
        List<Object> list = new ArrayList<>(1);
        // es
        com.xlauncher.entity.Component es = this.componentDao.getComponentByAbbr("es");
        int esStatus = this.getESStatus();
        es.setComponentStatus(esStatus);
        // ics
        com.xlauncher.entity.Component ics = this.componentDao.getComponentByAbbr("ics");
        int icsStatus = this.getICSStatus();
        ics.setComponentStatus(icsStatus);
        // dim
        com.xlauncher.entity.Component dim = this.componentDao.getComponentByAbbr("dim");
        int dimStatus = this.getDIMStatus();
        dim.setComponentStatus(dimStatus);
        // cms
        com.xlauncher.entity.Component cms = this.componentDao.getComponentByAbbr("cms");
        int cmsStatus = this.getCMSStatus();
        cms.setComponentStatus(cmsStatus);
        list.add(es);
        list.add(ics);
        list.add(dim);
        list.add(cms);
        return list;
    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                heartBeat();
            }
        },1000 * 60,1000 * 60 * 10);
    }
}
