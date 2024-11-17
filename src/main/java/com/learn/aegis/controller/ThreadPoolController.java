package com.learn.aegis.controller;

import com.learn.aegis.service.ThreadPoolManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ThreadPoolController类用于管理线程池的动态配置和状态监控
 */
@RestController
@RequestMapping("/threadpool")
public class ThreadPoolController {

    // 自动注入线程池管理服务
    @Autowired
    private ThreadPoolManagementService threadPoolManagementService;

    /**
     * 调整线程池的大小
     *
     * @param corePoolSize 核心线程数
     * @param maxPoolSize 最大线程数
     * @return 返回线程池调整后的大小信息
     */
    @PostMapping("/resize")
    public String resizeThreadPool(@RequestParam int corePoolSize, @RequestParam int maxPoolSize) {
        threadPoolManagementService.resizeThreadPool(corePoolSize, maxPoolSize);
        return "ThreadPool resized to CorePoolSize: " + corePoolSize + ", MaxPoolSize: " + maxPoolSize;
    }

    /**
     * 获取线程池的状态
     *
     * @return 返回线程池的当前状态信息
     */
    @GetMapping("/status")
    public String getThreadPoolStatus() {
        return threadPoolManagementService.getThreadPoolStatus();
    }

    /**
     * 更新线程池的配置
     *
     * @param corePoolSize 核心线程数
     * @param maxPoolSize 最大线程数
     * @param queueCapacity 队列容量
     * @param keepAliveSeconds 线程存活时间（秒）
     * @return 返回线程池配置更新后的信息
     */
    @PostMapping("/updateConfig")
    public String updateThreadPoolConfig(@RequestParam int corePoolSize, @RequestParam int maxPoolSize, @RequestParam int queueCapacity, @RequestParam int keepAliveSeconds) {
        threadPoolManagementService.updateThreadPoolConfig(corePoolSize, maxPoolSize, queueCapacity, keepAliveSeconds);
        return "ThreadPool configuration updated.";
    }
}
