/**
 * Copyright: @V1.0
 * Company:   www.xlauncher.com
 */
package com.xlauncher.util;

import com.xlauncher.dao.ComponentDao;;
import com.xlauncher.entity.Service;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Interface for camera log that posted by CMS or other systems.
 * @author mao ye
 * @since 2018-02-01
 */
@Component
public class ServiceUtil {
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    ComponentDao componentDao;
    private static Logger logger = Logger.getLogger(ServiceUtil.class);
    /** 从数据库中的读取dim的IP和port然后将cms和k8s的服务信息转发给dim*/
    public void synchro() {
        com.xlauncher.entity.Component serDim = componentDao.getComponentByAbbr("dim");
        com.xlauncher.entity.Component serIcs = componentDao.getComponentByAbbr("ics");
        com.xlauncher.entity.Component serEs = componentDao.getComponentByAbbr("es");

        if(serDim!=null) {
            String url = "http://" + serDim.getComponentIp() + ":" + serDim.getComponentPort() + "/dim/service";
            try {
                com.xlauncher.entity.Component component = componentDao.getComponentByAbbr("cms");
                Service service = new Service();
                service.setSerId(component.getId());
                service.setSerIp(component.getComponentIp());
                service.setSerName(component.getComponentAbbr());
                service.setSerPort(component.getComponentPort());
                restTemplate.postForEntity(url, service, String.class);
            } catch (RestClientException e) {
                logger.error("ServiceUtil.synchro() - 向DIM同步CMS服务信息错误！" + e + " , url:" + url);
            }
            try {
                com.xlauncher.entity.Component componentCMS = componentDao.getComponentByAbbr("ics");
                Service serviceICS = new Service();
                serviceICS.setSerId(componentCMS.getId());
                serviceICS.setSerName(componentCMS.getComponentAbbr());
                serviceICS.setSerIp(componentCMS.getComponentIp());
                serviceICS.setSerPort(componentCMS.getComponentPort().split(",")[1]);
                restTemplate.postForEntity(url, serviceICS, String.class);
            } catch (RestClientException e) {
                logger.error("ServiceUtil.synchro() - 向DIM同步ICS服务信息错误！" + e + " , url:" + url);
            }
        }
        if(serIcs!=null) {
            String url = "http://" + serIcs.getComponentIp() + ":" + serIcs.getComponentPort().split(",")[0] + "/service";
            try {
                restTemplate.postForEntity(url, componentDao.getComponentByAbbr("cms"), String.class);
            } catch (RestClientException e) {
                logger.error("ServiceUtil.synchro() - 向ICS同步ES服务信息错误！" + e + " , url:" + url);
            }
        }
        if(serEs!=null) {
            String url = "http://" + serEs.getComponentIp() + ":" + serEs.getComponentPort() + "/es/service";
            try {
                com.xlauncher.entity.Component componentES = componentDao.getComponentByAbbr("cms");
                Service serviceES = new Service();
                serviceES.setSerId(componentES.getId());
                serviceES.setSerIp(componentES.getComponentIp());
                serviceES.setSerPort(componentES.getComponentPort());
                serviceES.setSerName(componentES.getComponentAbbr());
                restTemplate.put(url, serviceES, String.class);
            } catch (RestClientException e) {
                logger.error("ServiceUtil.synchro() - 向ES同步CMS服务信息错误！" + e + " , url:" + url);
            }
        }
    }
}
