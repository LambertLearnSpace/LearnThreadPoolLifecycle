package com.learn.aegis.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ThreadPoolMonitor {
    private String poolType;
    private String status;
    private int corePoolSize;
    private int maximumPoolSize;
    private int activeThreads;
    private long completedTasks;
    private int queueSize;
    private String queueType;
    private boolean isShutdown;
    private boolean isTerminated;
}