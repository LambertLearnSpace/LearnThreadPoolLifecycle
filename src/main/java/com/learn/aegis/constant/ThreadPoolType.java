package com.learn.aegis.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 线程池类型的枚举类
 * 它定义了各种类型的线程池，每种类型都有特定的用途和行为
 * 使用Lombok的@Getter注解自动生成description字段的getter方法
 * 使用Lombok的@RequiredArgsConstructor注解自动生成包含所有final字段的构造方法
 */
@Getter
@RequiredArgsConstructor
public enum ThreadPoolType {
    /**
     * 固定大小的线程池
     * 适合用于负载重的长期运行任务
     */
    FIXED("Fixed Thread Pool"),
    /**
     * 可缓存的线程池
     * 适合用于执行大量短期异步任务
     */
    CACHED("Cached Thread Pool"),
    /**
     * 单线程池
     * 适合用于需要保证任务顺序执行的场景
     */
    SINGLE("Single Thread Pool"),
    /**
     * 定时调度线程池
     * 适合用于需要定时执行或周期性执行的任务
     */
    SCHEDULED("Scheduled Thread Pool"),
    /**
     * 工作窃取线程池
     * 适合用于多核处理器的任务，可以提高CPU利用率
     */
    WORK_STEALING("Work Stealing Pool");

    // 线程池类型的描述
    private final String description;
}
