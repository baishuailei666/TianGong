package com.xlauncher.service.impl;

import com.xlauncher.entity.Channel;
import com.xlauncher.service.UpdateModelService;
import com.xlauncher.util.ConstantClassUtil;
import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



/**
 * 更新服务模块实现类
 * @author YangDengcheng
 * @date 2018/1/24 10:23
 */
@Service("updateModelService")
public class UpdateModelServiceImpl implements UpdateModelService {

    private static final Logger LOGGER = Logger.getLogger(UpdateModelServiceImpl.class);
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders httpHeaders;
    private HttpEntity<String> httpEntity;


    @Override
    public void updateMsgToCMS(Channel channel) {
        String reqJsonStr = channel.getString();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpEntity = new HttpEntity<>(reqJsonStr,httpHeaders);

        try {
            restTemplate.exchange(ConstantClassUtil.CMS_LOCATION, HttpMethod.PUT,httpEntity,String.class);
            LOGGER.info("[URL:"+ ConstantClassUtil.CMS_LOCATION +",Methods=[PUT]] Invoke HTTP request successfully");
        }catch (Exception e){
            LOGGER.error("[URL:"+ ConstantClassUtil.CMS_LOCATION +",Methods=[PUT]] Invoke HTTP request unsuccessfully");
            e.printStackTrace();
        }

    }
}
