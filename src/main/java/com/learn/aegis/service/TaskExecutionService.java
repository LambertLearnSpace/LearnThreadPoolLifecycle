package com.learn.aegis.service;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TaskExecutionService {

    public void executeTask(Runnable task) {
        try {
            task.run();
            log.info("Task executed successfully.");
        } catch (Exception e) {
            log.error("Error executing task", e);
        }
    }
} 