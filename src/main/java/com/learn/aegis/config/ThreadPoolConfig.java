package com.learn.aegis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置类
 */
@Configuration
public class ThreadPoolConfig {
    /**
     * 配置并创建一个ThreadPoolTaskExecutor bean
     * 用于处理异步任务执行，提供线程池的配置属性
     *
     * @return ThreadPoolTaskExecutor实例，配置好线程池属性后初始化
     */
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程池大小
        executor.setCorePoolSize(10);
        // 设置最大线程池大小
        executor.setMaxPoolSize(20);
        // 设置线程池所使用的缓冲队列大小
        executor.setQueueCapacity(50);
        // 设置线程名称前缀，方便识别和调试
        executor.setThreadNamePrefix("CustomThreadPool-");
        // 设置线程存活时间（秒），当线程池大于核心线程池时，此参数表示空闲线程在终止前等待新任务的秒数
        executor.setKeepAliveSeconds(60);
        // 设置拒绝执行任务的策略为调用者运行，即由调用线程自己执行该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 设置线程池关闭时等待所有任务完成再关闭
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 初始化线程池
        executor.initialize();
        return executor;
    }
}

