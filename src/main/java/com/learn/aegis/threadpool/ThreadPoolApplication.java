package com.learn.aegis.threadpool;

import com.learn.aegis.constant.ThreadPoolType;
import com.learn.aegis.model.ThreadPoolMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.concurrent.*;


@Service
@Slf4j
public class ThreadPoolApplication {
    public ThreadPoolMonitor executeTask(ThreadPoolType type) {
        ThreadPoolExecutor executor = createThreadPool(type);

        log.info("Initial state: {}", getPoolStatus(executor));

        executor.execute(() -> {
            log.info("Task executing in pool: {}", type);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        log.info("Running state: {}", getPoolStatus(executor));

        executor.shutdown();
        log.info("After shutdown: {}", getPoolStatus(executor));

        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        log.info("Final state: {}", getPoolStatus(executor));

        return buildMonitor(executor, type);
    }

    private ThreadPoolExecutor createThreadPool(ThreadPoolType type) {
        switch (type) {
            case FIXED:
                return (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
            case CACHED:
                return (ThreadPoolExecutor) Executors.newCachedThreadPool();
            case SINGLE:
                return (ThreadPoolExecutor) Executors.newSingleThreadExecutor();
            case SCHEDULED:
                return (ThreadPoolExecutor) Executors.newScheduledThreadPool(2);
            case WORK_STEALING:
                return (ThreadPoolExecutor) Executors.newWorkStealingPool();
            default:
                throw new IllegalArgumentException("Unsupported pool type");
        }
    }

    private String getPoolStatus(ThreadPoolExecutor executor) {
        return String.format("Pool Size: %d, Active Threads: %d, Completed Tasks: %d, Is Shutdown: %b, Is Terminated: %b",
                executor.getPoolSize(), executor.getActiveCount(), executor.getCompletedTaskCount(),
                executor.isShutdown(), executor.isTerminated());
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
