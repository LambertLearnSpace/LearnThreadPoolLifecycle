package com.learn.aegis.controller;

import com.learn.aegis.service.ThreadLifecycleService;
import com.learn.aegis.service.ThreadPoolManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 控制线程生命周期和线程池管理的控制器类
 */
@RestController
@RequestMapping("/thread")
public class ThreadLifecycleController {

    // 自动注入线程生命周期服务
    @Autowired
    private ThreadLifecycleService threadLifecycleService;

    // 自动注入线程池管理服务
    @Autowired
    private ThreadPoolManagementService threadPoolManagementService;

    /**
     * 启动线程
     *
     * @return 线程启动的结果信息
     */
    @GetMapping("/start")
    public String startThread() {
        return threadLifecycleService.startThread();
    }

    /**
     * 调整线程池大小
     *
     * @param corePoolSize 线程池的核心线程数
     * @param maxPoolSize 线程池的最大线程数
     * @return 线程池调整后的信息
     */
    @PostMapping("/resizeThreadPool")
    public String resizeThreadPool(@RequestParam int corePoolSize, @RequestParam int maxPoolSize) {
        threadPoolManagementService.resizeThreadPool(corePoolSize, maxPoolSize);
        return "ThreadPool resized to CorePoolSize: " + corePoolSize + ", MaxPoolSize: " + maxPoolSize;
    }

    /**
     * 获取线程池状态
     *
     * @return 线程池当前的状态信息
     */
    @GetMapping("/getThreadPoolStatus")
    public String getThreadPoolStatus() {
        return threadPoolManagementService.getThreadPoolStatus();
    }

    /**
     * 更新线程池配置
     *
     * @param corePoolSize 线程池的核心线程数
     * @param maxPoolSize 线程池的最大线程数
     * @param queueCapacity 工作队列的最大容量
     * @param keepAliveSeconds 线程空闲时间（单位：秒）
     * @return 更新配置后的信息
     */
    @PostMapping("/updateThreadPoolConfig")
    public String updateThreadPoolConfig(@RequestParam int corePoolSize, @RequestParam int maxPoolSize, @RequestParam int queueCapacity, @RequestParam int keepAliveSeconds) {
        threadPoolManagementService.updateThreadPoolConfig(corePoolSize, maxPoolSize, queueCapacity, keepAliveSeconds);
        return "ThreadPool configuration updated.";
    }
}
