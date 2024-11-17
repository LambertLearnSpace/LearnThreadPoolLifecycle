package com.learn.aegis.model;

import lombok.Builder;
import lombok.Data;

/**
 * ThreadPoolMonitor 类用于监控线程池的状态和关键指标。
 * 它提供了线程池的类型、运行状态、核心线程数、最大线程数、活动线程数、已完成任务数、队列大小和类型，
 * 以及线程池是否已关闭或终止等信息。
 */
@Data
@Builder
public class ThreadPoolMonitor {
    /**
     * 线程池的类型，用于区分不同配置或用途的线程池。
     */
    private String poolType;

    /**
     * 线程池的当前状态，表示线程池是否在运行、关闭或终止。
     */
    private String status;

    /**
     * 线程池的核心线程数，即线程池尝试保持的线程数，即使它们处于空闲状态。
     */
    private int corePoolSize;

    /**
     * 线程池的最大线程数，表示可以创建的最大线程数来处理任务。
     */
    private int maximumPoolSize;

    /**
     * 当前正在执行任务的线程数。
     */
    private int activeThreads;

    /**
     * 线程池已完成的任务总数。
     */
    private long completedTasks;

    /**
     * 等待执行的任务在队列中的数量。
     */
    private int queueSize;

    /**
     * 线程池使用的队列类型，不同类型的队列可能具有不同的特性和用途。
     */
    private String queueType;

    /**
     * 表示是否已启动线程池的关闭操作。已关闭的线程池不再接受新任务。
     */
    private boolean isShutdown;

    /**
     * 表示线程池中的所有线程是否已完全停止。已终止的线程池已完成所有任务并停止了所有线程。
     */
    private boolean isTerminated;
}
