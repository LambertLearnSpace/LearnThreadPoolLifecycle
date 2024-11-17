package com.learn.aegis.controller;

import com.learn.aegis.service.SynchronizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

// 控制器类，处理与同步相关的请求
@RestController
@RequestMapping("/sync")
public class SynchronizationController {

    // 自动注入同步服务，用于执行计数操作
    @Autowired
    private SynchronizationService synchronizationService;

    /**
     * 增加计数器的值
     * 此方法无参数
     * 返回值: 增加后的计数器值
     */
    @GetMapping("/increase")
    public int increaseCount() {
        return synchronizationService.increaseCount();
    }

    /**
     * 使用锁来增加计数器的值，以确保线程安全
     * 此方法无参数
     * 返回值: 增加后的计数器值
     */
    @GetMapping("/increaseWithLock")
    public int increaseCountWithLock() {
        return synchronizationService.increaseCountWithLock();
    }
}
