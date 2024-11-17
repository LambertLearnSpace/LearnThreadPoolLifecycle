package com.learn.aegis.service;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * 任务执行服务类，提供任务执行的服务
 * 使用Spring的@Service注解标记为一个服务类
 * 使用Slf4j提供的日志工具记录日志信息
 */
@Service
@Slf4j
public class TaskExecutionService {

    /**
     * 执行一个给定的任务
     * 该方法接收一个Runnable类型的参数，代表一个可执行的任务
     * 如果任务执行成功，记录一条信息日志
     * 如果任务执行过程中发生异常，记录一条错误日志，并保存异常信息
     *
     * @param task 一个实现了Runnable接口的任务对象，表示一个可执行的任务
     */
    public void executeTask(Runnable task) {
        try {
            // 执行任务
            task.run();
            log.info("Task executed successfully.");
        } catch (Exception e) {
            log.error("Error executing task", e);
        }
    }
}
