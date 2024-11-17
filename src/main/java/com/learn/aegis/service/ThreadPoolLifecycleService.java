package com.learn.aegis.service;

import com.learn.aegis.constant.ThreadPoolType;
import com.learn.aegis.model.ThreadPoolMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * 线程池生命周期管理服务类
 * 该类负责管理不同类型的线程池的生命周期，包括线程池的创建、任务执行和关闭
 */
@Slf4j
@Service
public class ThreadPoolLifecycleService {

    // 存储不同类型的线程池
    private final ConcurrentHashMap<ThreadPoolType, ExecutorService> threadPools = new ConcurrentHashMap<>();

    /**
     * 构造方法，初始化各种类型的线程池
     */
    public ThreadPoolLifecycleService() {
        // 初始化固定大小的线程池，适合负载重的长期运行任务
        threadPools.put(ThreadPoolType.FIXED, Executors.newFixedThreadPool(2));
        // 初始化可缓存的线程池，适合执行大量短期异步任务
        threadPools.put(ThreadPoolType.CACHED, Executors.newCachedThreadPool());
        // 初始化单线程化的线程池，适合需要保证顺序执行的场景
        threadPools.put(ThreadPoolType.SINGLE, Executors.newSingleThreadExecutor());
        // 初始化定时调度的线程池，适合需要定时或延时执行任务的场景
        threadPools.put(ThreadPoolType.SCHEDULED, Executors.newScheduledThreadPool(2));
        // 初始化工作窃取的线程池，适合大量并发执行的任务，可以提高CPU利用率
        threadPools.put(ThreadPoolType.WORK_STEALING, Executors.newWorkStealingPool());
    }

    /**
     * 执行任务方法
     * 根据指定的线程池类型，提交任务到相应的线程池中执行
     *
     * @param type 线程池类型，决定了任务将在哪种类型的线程池中执行
     * @return 返回一个线程池监控对象，包含线程池的当前状态和指标
     */
    public ThreadPoolMonitor executeTask(ThreadPoolType type) {
        // 根据线程池类型获取对应的线程池实例
        ExecutorService executor = threadPools.get(type);
        // 如果给定的线程池类型无效或未定义，则抛出异常
        if (executor == null) {
            throw new IllegalArgumentException("Unsupported pool type");
        }

        // 日志记录：提交任务到指定类型的线程池
        log.info("Submitting task to {} pool", type);
        // 提交任务到线程池执行
        executor.submit(() -> {
            // 日志记录：任务正在指定类型的线程池中执行
            log.info("Task executing in pool: {}", type);
            // 模拟任务执行时间
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // 如果线程被中断，则恢复中断状态
                Thread.currentThread().interrupt();
            }
        });

        // 构建并返回线程池监控对象，用于监控线程池的当前状态和指标
        return buildMonitor((ThreadPoolExecutor) executor, type);
    }

    /**
     * 平滑关闭线程池方法
     * 提交完所有任务后关闭线程池，不再接受新任务
     *
     * @param type 要关闭的线程池类型
     * @return 返回一个线程池监控对象，包含线程池的当前状态和指标
     */
    public ThreadPoolMonitor shutdownPool(ThreadPoolType type) {
        // 根据类型获取对应的线程池
        ExecutorService executor = threadPools.get(type);
        // 如果线程池存在，则发起关闭线程池的请求
        if (executor != null) {
            // 尝试停止所有活动执行任务
            executor.shutdown();
            // 记录日志，表明线程池关闭已启动
            log.info("{} pool shutdown initiated", type);
        }
        // 构建并返回线程池监控对象
        return buildMonitor((ThreadPoolExecutor) executor, type);
    }

    /**
     * 立即关闭线程池方法
     * 尝试停止所有活动执行任务，返回未执行的任务列表
     *
     * @param type 要立即关闭的线程池类型
     * @return 返回一个线程池监控对象，包含线程池的当前状态和指标
     */
    public ThreadPoolMonitor shutdownNowPool(ThreadPoolType type) {
        // 根据线程池类型获取对应的ExecutorService实例
        ExecutorService executor = threadPools.get(type);
        // 如果executor不为空，则尝试关闭线程池
        if (executor != null) {
            executor.shutdownNow();
            // 记录日志，表明已启动线程池的立即关闭流程
            log.info("{} pool immediate shutdown initiated", type);
        }
        // 构建并返回线程池监控对象，用于后续监控线程池的关闭状态和其他指标
        return buildMonitor((ThreadPoolExecutor) executor, type);
    }

    /**
     * 构建线程池监控信息方法
     * 根据线程池执行器和类型，构建一个包含线程池详细状态的监控对象
     *
     * @param executor 线程池执行器，从中获取监控信息
     * @param type 线程池类型，用于监控信息中
     * @return 返回一个构建好的线程池监控对象
     */
    private ThreadPoolMonitor buildMonitor(ThreadPoolExecutor executor, ThreadPoolType type) {
        // 使用ThreadPoolMonitor的构建器模式创建一个新的实例
        return ThreadPoolMonitor.builder()
                // 设置线程池类型描述
                .poolType(type.getDescription())
                // 根据线程池是否已终止，设置其状态
                .status(executor.isTerminated() ? "TERMINATED" : "RUNNING")
                // 设置核心线程池大小
                .corePoolSize(executor.getCorePoolSize())
                // 设置最大线程池大小
                .maximumPoolSize(executor.getMaximumPoolSize())
                // 设置当前活动线程数
                .activeThreads(executor.getActiveCount())
                // 设置已完成任务数
                .completedTasks(executor.getCompletedTaskCount())
                // 设置队列中任务数
                .queueSize(executor.getQueue().size())
                // 设置队列类型
                .queueType(executor.getQueue().getClass().getSimpleName())
                // 设置线程池是否已关闭
                .isShutdown(executor.isShutdown())
                // 设置线程池是否已终止
                .isTerminated(executor.isTerminated())
                // 完成构建
                .build();
    }
}
