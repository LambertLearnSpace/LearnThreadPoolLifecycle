package com.learn.aegis.service;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


/**
 * ThreadLifecycleService 类提供了线程生命周期管理的相关服务
 * 它包含启动线程的方法，并记录线程的启动和睡眠日志
 */
@Slf4j
@Service
public class ThreadLifecycleService {

    /**
     * 启动一个新的线程，该线程将在启动后睡眠1秒
     *
     * @return 返回表示线程已启动的消息"Thread started"
     */
    public String startThread() {
        // 创建一个新的线程，线程执行的任务定义在lambda表达式中
        Thread thread = new Thread(() -> {
            try {
                // 线程睡眠1秒，模拟长时间运行的任务
                Thread.sleep(1000);
                // 记录日志，表明线程已完成睡眠
                log.info("Thread has completed sleeping.");
            } catch (InterruptedException e) {
                // 如果线程在睡眠期间被中断，重新设置中断状态并记录错误日志
                Thread.currentThread().interrupt();
                log.error("Thread was interrupted.", e);
            }
        });
        // 启动线程
        thread.start();
        // 记录日志，表明线程已启动
        log.info("Thread started.");
        // 返回表示线程已启动的消息
        return "Thread started";
    }
}
