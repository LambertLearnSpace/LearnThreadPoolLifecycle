package com.learn.aegis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
/**
 * 线程池管理服务类，提供线程池的动态调整、状态获取和配置更新等功能
 */
@Service
@Slf4j
public class ThreadPoolManagementService {

    /**
     * 自动注入的线程池任务执行器
     *
     * 该线程池任务执行器用于执行异步任务，通过Spring的自动注入功能（@Autowired）获取实例
     * 这意味着在需要执行异步任务时，可以直接使用该执行器，无需手动创建或管理线程池
     */
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 调整线程池的大小
     *
     * @param corePoolSize 核心线程池大小
     * @param maxPoolSize 最大线程池大小
     */
    public void resizeThreadPool(int corePoolSize, int maxPoolSize) {
        // 设置线程池的核心线程数
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        // 设置线程池的最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        // 记录调整线程池大小的日志信息
        log.info("Resized thread pool to CorePoolSize: {}, MaxPoolSize: {}", corePoolSize, maxPoolSize);
    }

    /**
     * 获取线程池的状态信息
     *
     * @return 线程池状态的字符串描述
     */
    public String getThreadPoolStatus() {
        // 使用String.format格式化线程池状态信息，包括活动线程数、线程池总大小、活动任务数、已完成任务数和队列大小
        return String.format("Active Threads: %d, Total Pool Size: %d, Active Tasks: %d, Completed Tasks: %d, Queue Size: %d",
                // 获取线程池的活跃线程数、线程池总大小、活动任务数、已完成任务数和队列大小
                threadPoolTaskExecutor.getActiveCount(),
                // 获取线程池的线程池大小
                threadPoolTaskExecutor.getPoolSize(),
                // 获取线程池的队列大小
                threadPoolTaskExecutor.getActiveCount(),
                // 获取线程池已完成的任务数
                threadPoolTaskExecutor.getThreadPoolExecutor().getCompletedTaskCount(),
                // 获取线程池的队列大小
                threadPoolTaskExecutor.getThreadPoolExecutor().getQueue().size());
    }

    /**
     * 更新线程池的配置
     *
     * @param corePoolSize 核心线程池大小
     * @param maxPoolSize 最大线程池大小
     * @param queueCapacity 队列容量
     * @param keepAliveSeconds 线程存活时间（秒）
     */
    public void updateThreadPoolConfig(int corePoolSize, int maxPoolSize, int queueCapacity, int keepAliveSeconds) {
        // 设置线程池的核心线程数
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        // 设置线程池的最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        // 设置线程池的工作队列容量
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        // 设置线程的存活时间
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        // 初始化线程池
        threadPoolTaskExecutor.initialize();
        // 记录更新线程池配置的日志
        log.info("Updated thread pool configuration to CorePoolSize: {}, MaxPoolSize: {}, QueueCapacity: {}, KeepAliveSeconds: {}", corePoolSize, maxPoolSize, queueCapacity, keepAliveSeconds);
    }
}
