package com.learn.aegis.service;

import com.learn.aegis.constant.ThreadPoolType;
import com.learn.aegis.model.ThreadPoolMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
@Slf4j
public class ThreadPoolLifecycleService {

    private final ConcurrentHashMap<ThreadPoolType, ExecutorService> threadPools = new ConcurrentHashMap<>();

    public ThreadPoolLifecycleService() {
        threadPools.put(ThreadPoolType.FIXED, Executors.newFixedThreadPool(2));
        threadPools.put(ThreadPoolType.CACHED, Executors.newCachedThreadPool());
        threadPools.put(ThreadPoolType.SINGLE, Executors.newSingleThreadExecutor());
        threadPools.put(ThreadPoolType.SCHEDULED, Executors.newScheduledThreadPool(2));
        threadPools.put(ThreadPoolType.WORK_STEALING, Executors.newWorkStealingPool());
    }

    public ThreadPoolMonitor executeTask(ThreadPoolType type) {
        ExecutorService executor = threadPools.get(type);
        if (executor == null) {
            throw new IllegalArgumentException("Unsupported pool type");
        }

        log.info("Submitting task to {} pool", type);
        executor.submit(() -> {
            log.info("Task executing in pool: {}", type);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        return buildMonitor((ThreadPoolExecutor) executor, type);
    }

    public ThreadPoolMonitor shutdownPool(ThreadPoolType type) {
        ExecutorService executor = threadPools.get(type);
        if (executor != null) {
            executor.shutdown();
            log.info("{} pool shutdown initiated", type);
        }
        return buildMonitor((ThreadPoolExecutor) executor, type);
    }

    public ThreadPoolMonitor shutdownNowPool(ThreadPoolType type) {
        ExecutorService executor = threadPools.get(type);
        if (executor != null) {
            executor.shutdownNow();
            log.info("{} pool immediate shutdown initiated", type);
        }
        return buildMonitor((ThreadPoolExecutor) executor, type);
    }

    private ThreadPoolMonitor buildMonitor(ThreadPoolExecutor executor, ThreadPoolType type) {
        return ThreadPoolMonitor.builder()
                .poolType(type.getDescription())
                .status(executor.isTerminated() ? "TERMINATED" : "RUNNING")
                .corePoolSize(executor.getCorePoolSize())
                .maximumPoolSize(executor.getMaximumPoolSize())
                .activeThreads(executor.getActiveCount())
                .completedTasks(executor.getCompletedTaskCount())
                .queueSize(executor.getQueue().size())
                .queueType(executor.getQueue().getClass().getSimpleName())
                .isShutdown(executor.isShutdown())
                .isTerminated(executor.isTerminated())
                .build();
    }
}