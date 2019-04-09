package com.xlauncher.service;

import com.xlauncher.entity.Channel;

/**
 * 更新服务模块接口
 * @author YangDengcheng
 * @date 2018/1/24 10:21
 */
public interface UpdateModelService {

    /**
     * 推送状态至CMS
     * @param channel
     */
    void updateMsgToCMS(Channel channel);
}
