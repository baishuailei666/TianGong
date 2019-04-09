package com.xlauncher.utils;

import com.xlauncher.service.runner.RestTemplateRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 线程池
 * @author baishuailei
 * @date 2018-07-25
 */
@Component
public class ThreadUtil {
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public void ThreadUtil(RestTemplateRunner restTemplateRunner) {
        this.threadPoolTaskExecutor.execute(restTemplateRunner);
    }
}
