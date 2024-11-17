package com.learn.aegis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ThreadPoolManagementService {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public void resizeThreadPool(int corePoolSize, int maxPoolSize) {
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        log.info("Resized thread pool to CorePoolSize: {}, MaxPoolSize: {}", corePoolSize, maxPoolSize);
    }

    public String getThreadPoolStatus() {
        return String.format("Active Threads: %d, Total Pool Size: %d, Active Tasks: %d, Completed Tasks: %d, Queue Size: %d",
                threadPoolTaskExecutor.getActiveCount(),
                threadPoolTaskExecutor.getPoolSize(),
                threadPoolTaskExecutor.getActiveCount(),
                threadPoolTaskExecutor.getThreadPoolExecutor().getCompletedTaskCount(),
                threadPoolTaskExecutor.getThreadPoolExecutor().getQueue().size());
    }

    public void updateThreadPoolConfig(int corePoolSize, int maxPoolSize, int queueCapacity, int keepAliveSeconds) {
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        threadPoolTaskExecutor.initialize();
        log.info("Updated thread pool configuration to CorePoolSize: {}, MaxPoolSize: {}, QueueCapacity: {}, KeepAliveSeconds: {}", corePoolSize, maxPoolSize, queueCapacity, keepAliveSeconds);
    }
} 