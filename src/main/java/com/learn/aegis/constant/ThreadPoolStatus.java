package com.learn.aegis.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 线程池状态枚举类
 * 用于表示线程池的生命周期状态，包括运行、关闭、停止、整理和终止
 */
@Getter
@RequiredArgsConstructor
public enum ThreadPoolStatus {
    // 线程池正在运行的状态
    RUNNING("Running"),
    // 线程池已关闭的状态，此时线程池已停止接受新任务，但仍在处理未完成的任务
    SHUTDOWN("Shutdown"),
    // 线程池已停止的状态，此时所有任务处理已完成，线程池正在释放资源
    STOP("Stop"),
    // 线程池正在整理的状态，此时线程池已停止，正在等待所有任务完全终止
    TIDYING("Tidying"),
    // 线程池已终止的状态，此时所有任务已终止，资源已释放
    TERMINATED("Terminated");

    // 状态的描述信息
    private final String description;
}
