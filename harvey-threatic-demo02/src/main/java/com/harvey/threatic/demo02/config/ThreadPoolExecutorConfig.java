package com.harvey.threatic.demo02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-07
 */
@Configuration
public class ThreadPoolExecutorConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor0201() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1,
            3,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );
        
        return threadPoolExecutor;
    }
    
    @Bean
    public ThreadPoolExecutor threadPoolExecutor0202() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1,
            3,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );
        
        return threadPoolExecutor;
    }
}
