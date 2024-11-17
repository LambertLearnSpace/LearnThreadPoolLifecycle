package com.learn.aegis.controller;

import com.learn.aegis.service.ThreadLifecycleService;
import com.learn.aegis.service.ThreadPoolManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thread")
public class ThreadLifecycleController {

    @Autowired
    private ThreadLifecycleService threadLifecycleService;
    @Autowired
    private ThreadPoolManagementService threadPoolManagementService;

    @GetMapping("/start")
    public String startThread() {
        return threadLifecycleService.startThread();
    }

    @PostMapping("/resizeThreadPool")
    public String resizeThreadPool(@RequestParam int corePoolSize, @RequestParam int maxPoolSize) {
        threadPoolManagementService.resizeThreadPool(corePoolSize, maxPoolSize);
        return "ThreadPool resized to CorePoolSize: " + corePoolSize + ", MaxPoolSize: " + maxPoolSize;
    }

    @GetMapping("/getThreadPoolStatus")
    public String getThreadPoolStatus() {
        return threadPoolManagementService.getThreadPoolStatus();
    }

    @PostMapping("/updateThreadPoolConfig")
    public String updateThreadPoolConfig(@RequestParam int corePoolSize, @RequestParam int maxPoolSize, @RequestParam int queueCapacity, @RequestParam int keepAliveSeconds) {
        threadPoolManagementService.updateThreadPoolConfig(corePoolSize, maxPoolSize, queueCapacity, keepAliveSeconds);
        return "ThreadPool configuration updated.";
    }
} 