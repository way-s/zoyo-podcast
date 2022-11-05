package com.zoyo.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: mxx
 * @Description:
 */
@EnableAsync
@EnableScheduling
@Configuration
public class ThreadPoolConfig {

    /**
     * 线程池配置
     */
    @Bean("tPool1")
    public Executor taskExecutor() {
        // 线程池任务执行器
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心池大小
        executor.setCorePoolSize(5);
        // 设置最大线程数
        executor.setMaxPoolSize(20);
        // 设置队列容量，队列大小
        executor.setQueueCapacity(20);
        // 设置保持活动秒数
        executor.setKeepAliveSeconds(60);
        // 设置线程名称前缀
        executor.setThreadNamePrefix("async-task-1-");
        // 设置拒绝执行处理程序
        // RejectedExecutionHandler：当 pool已经达到 max-size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 执行初始化
        executor.initialize();
        return executor;
    }
}
