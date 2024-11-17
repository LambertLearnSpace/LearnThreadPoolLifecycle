package com.learn.aegis.threadpool;

import com.learn.aegis.constant.ThreadPoolType;
import com.learn.aegis.model.ThreadPoolMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.concurrent.*;


/**
 * ThreadPoolApplication 类是应用中的一个服务类，专注于线程池的管理与使用
 * 通过注解 @Service 标记，表明这是一个被 Spring 框架管理的 Bean，可以被自动扫描并注入到其他组件中
 * 类级别的注释解释了该类的主要功能和用途，帮助开发者理解这个类的作用
 */
@Slf4j
@Service
public class ThreadPoolApplication {

    /**
     * 执行任务并监控线程池状态
     * 本方法根据指定的线程池类型创建线程池，并提交一个简单任务进行执行，同时记录线程池状态
     *
     * @param type 线程池类型，用于区分不同配置的线程池
     * @return 返回一个ThreadPoolMonitor对象，用于进一步监控线程池状态
     */
    public ThreadPoolMonitor executeTask(ThreadPoolType type) {
        // 根据线程池类型创建线程池
        ThreadPoolExecutor executor = createThreadPool(type);

        // 记录线程池初始状态
        log.info("Initial state: {}", getPoolStatus(executor));

        // 提交任务到线程池执行
        executor.execute(() -> {
            // 记录任务执行时的线程池状态
            log.info("Task executing in pool: {}", type);
            try {
                // 模拟任务执行时间
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // 中断当前线程，恢复中断状态
                Thread.currentThread().interrupt();
            }
        });

        // 记录任务提交后线程池运行状态
        log.info("Running state: {}", getPoolStatus(executor));

        // 关闭线程池，不再接受新任务
        executor.shutdown();
        // 记录线程池关闭后的状态
        log.info("After shutdown: {}", getPoolStatus(executor));

        try {
            // 等待线程池中所有任务完成，最长等待时间为5秒
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // 如果超过5秒仍有任务未完成，则立即关闭线程池并中断所有任务
            executor.shutdownNow();
            // 中断当前线程，恢复中断状态
            Thread.currentThread().interrupt();
        }

        // 记录线程池最终状态
        log.info("Final state: {}", getPoolStatus(executor));

        // 构建并返回线程池监控对象
        return buildMonitor(executor, type);
    }

    /**
     * 创建指定类型的线程池
     *
     * @param type 线程池的类型，决定了线程池的特性
     * @return 根据指定类型创建的线程池对象
     * @throws IllegalArgumentException 如果传入的线程池类型不受支持
     */
    private ThreadPoolExecutor createThreadPool(ThreadPoolType type) {
        // 根据不同的线程池类型，创建并返回相应特性的线程池
        switch (type) {
            case FIXED:
                // 创建一个固定大小的线程池，适用于负载较重、需要控制并发数的场景
                return (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
            case CACHED:
                // 创建一个可缓存的线程池，适用于执行大量耗时较短的任务
                return (ThreadPoolExecutor) Executors.newCachedThreadPool();
            case SINGLE:
                // 创建一个单线程的线程池，适用于需要保证任务顺序执行的场景
                return (ThreadPoolExecutor) Executors.newSingleThreadExecutor();
            case SCHEDULED:
                // 创建一个支持定时和周期性任务的线程池
                return (ThreadPoolExecutor) Executors.newScheduledThreadPool(2);
            case WORK_STEALING:
                // 创建一个工作窃取型的线程池，适用于多核处理器，能有效利用处理器资源
                return (ThreadPoolExecutor) Executors.newWorkStealingPool();
            default:
                // 如果传入的线程池类型不受支持，则抛出异常
                throw new IllegalArgumentException("Unsupported pool type");
        }
    }

    /**
     * 获取线程池的状态信息
     *
     * @param executor 线程池执行器对象，通过它可以获取线程池的各种状态信息
     * @return 返回格式化的字符串，包含线程池的大小、活跃线程数、完成的任务数以及线程池是否已关闭或终止
     */
    private String getPoolStatus(ThreadPoolExecutor executor) {
        // 使用String.format格式化线程池状态信息，包括池大小、活跃线程数、已完成任务数、是否关闭、是否终止
        return String.format("Pool Size: %d, Active Threads: %d, Completed Tasks: %d, Is Shutdown: %b, Is Terminated: %b",
                // 获取线程池的池大小、活跃线程数、已完成任务数
                executor.getPoolSize(), executor.getActiveCount(), executor.getCompletedTaskCount(),
                // 获取线程池是否已关闭或终止的状态
                executor.isShutdown(), executor.isTerminated());
    }

    /**
     * 构建线程池监控对象
     *
     * @param executor 线程池执行器，用于获取线程池的相关信息
     * @param type 线程池类型，用于标识线程池的用途或分类
     * @return 返回一个构建好的ThreadPoolMonitor对象，包含线程池的详细信息
     */
    private ThreadPoolMonitor buildMonitor(ThreadPoolExecutor executor, ThreadPoolType type) {
        // 使用ThreadPoolMonitor的构建器模式创建一个新的ThreadPoolMonitor对象
        return ThreadPoolMonitor.builder()
                // 设置线程池类型描述
                .poolType(type.getDescription())
                // 根据线程池是否已终止来判断其状态
                .status(executor.isTerminated() ? "TERMINATED" : "RUNNING")
                // 设置线程池的核心线程数
                .corePoolSize(executor.getCorePoolSize())
                // 设置线程池的最大线程数
                .maximumPoolSize(executor.getMaximumPoolSize())
                // 设置当前活动线程的数量
                .activeThreads(executor.getActiveCount())
                // 设置已完成任务的数量
                .completedTasks(executor.getCompletedTaskCount())
                // 设置队列中等待执行的任务数量
                .queueSize(executor.getQueue().size())
                // 设置线程池中任务队列的类型
                .queueType(executor.getQueue().getClass().getSimpleName())
                // 设置线程池是否已经调用shutdown方法
                .isShutdown(executor.isShutdown())
                // 设置线程池是否已经终止所有任务并关闭
                .isTerminated(executor.isTerminated())
                // 构建ThreadPoolMonitor对象
                .build();
    }
}
