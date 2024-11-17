package com.learn.aegis.service.impl;

import com.learn.aegis.service.SynchronizationService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
/**
 * SynchronizationServiceImpl类实现了SynchronizationService接口，提供同步操作的服务
 * 该类主要演示了不同同步机制的使用，包括synchronized关键字和Object的synchronized块
 */
@Service
@Slf4j
public class SynchronizationServiceImpl implements SynchronizationService {

    // 计数器，记录增加的次数
    private int count = 0;

    // 用作同步块的锁对象，提供比内置锁更细粒度的同步控制
    private final Object lock = new Object();

    /**
     * 使用synchronized关键字同步的方法
     * 该方法在每次调用时增加count的值，并记录日志
     *
     * @return 增加后的count值
     */
    @Override
    public synchronized int increaseCount() {
        // 使用synchronized关键字同步，确保只有一个线程可以执行这段代码
        count++;
        log.info("Count increased to {}", count);
        return count;
    }

    /**
     * 使用synchronized块进行同步的方法
     * 该方法在每次调用时增加count的值，并记录日志，与increaseCount方法不同的是，
     * 它使用外部定义的lock对象进行同步，而不是整个方法的锁
     *
     * @return 增加后的count值
     */
    @Override
    public int increaseCountWithLock() {
        // 使用外部定义的lock对象进行同步
        synchronized (lock) {
            // 使用synchronized块进行同步，确保只有一个线程可以执行这段代码
            count++;
            log.info("Count increased with lock to {}", count);
            return count;
        }
    }
}
