package com.learn.aegis.service;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ThreadLifecycleService {

    public String startThread() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                log.info("Thread has completed sleeping.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Thread was interrupted.", e);
            }
        });
        thread.start();
        log.info("Thread started.");
        return "Thread started";
    }
} 