package com.learn.aegis.service.impl;

import com.learn.aegis.service.SynchronizationService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SynchronizationServiceImpl implements SynchronizationService {

    private int count = 0;
    private final Object lock = new Object();

    @Override
    public synchronized int increaseCount() {
        count++;
        log.info("Count increased to {}", count);
        return count;
    }

    @Override
    public int increaseCountWithLock() {
        synchronized (lock) {
            count++;
            log.info("Count increased with lock to {}", count);
            return count;
        }
    }
} 