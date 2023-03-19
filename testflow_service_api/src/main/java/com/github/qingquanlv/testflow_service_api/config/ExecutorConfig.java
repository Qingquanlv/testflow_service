package com.github.qingquanlv.testflow_service_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author Qingquan Lv
 * @Version 1.0
 */
@Configuration
public class ExecutorConfig {

    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() throws InterruptedException{
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(5);
        executor.setKeepAliveSeconds(10);
        executor.setQueueCapacity(200);
        executor.setRejectedExecutionHandler((Runnable r, ThreadPoolExecutor exe) -> {
            // 利用BlockingQueue的特性，任务队列满时等待放入
            try {
                if (!exe.getQueue().offer(r, 30, TimeUnit.SECONDS)) {
                    throw new Exception("Task offer failed after 30 sec");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return executor;
    }


    @Bean(name = "opsTaskExecutor")
    public Executor getOpsAsyncExecutor() throws InterruptedException{
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(5);
        executor.setKeepAliveSeconds(10);
        executor.setQueueCapacity(200);
        executor.setRejectedExecutionHandler((Runnable r, ThreadPoolExecutor exe) -> {
            // 利用BlockingQueue的特性，任务队列满时等待放入
            try {
                if (!exe.getQueue().offer(r, 30, TimeUnit.SECONDS)) {
                    throw new Exception("Task offer failed after 30 sec");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return executor;
    }
}
