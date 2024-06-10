package com.harvey.threatic.starter.config;

import cn.hutool.core.util.StrUtil;
import com.harvey.threatic.starter.domain.model.dto.ThreadPoolExecutorUpdateMessage;
import com.harvey.threatic.starter.domain.model.eo.ThreadPoolExecutorProp;
import com.harvey.threatic.starter.domain.model.vo.ThreaticCacheKey;
import com.harvey.threatic.starter.domain.service.ThreadPoolExecutorService;
import com.harvey.threatic.starter.domain.service.ThreadPoolExecutorServiceImpl;
import com.harvey.threatic.starter.registry.RedisThreadPoolExecutorRegistry;
import com.harvey.threatic.starter.registry.ThreadPoolExecutorRegistry;
import com.harvey.threatic.starter.support.FastJsonRedissonCodec;
import com.harvey.threatic.starter.trigger.job.ThreadPoolExecutorPropReportJob;
import com.harvey.threatic.starter.trigger.listener.ThreadPoolExecutorPropUpdateMessageListener;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-09
 */
@AutoConfiguration
@EnableScheduling
@EnableConfigurationProperties({ThreaticProperties.class, ThreaticRedissonProperties.class})
public class ThreaticConfig {
    @Bean
    public RedissonClient redissonClient(
        ThreaticRedissonProperties threaticRedissonProperties
    ) {
        String password = threaticRedissonProperties.getPassword();
        String address = String.format("redis://%s:%s", threaticRedissonProperties.getHost(), threaticRedissonProperties.getPort());
        
        Config config = new Config();
        config.useSingleServer()
            .setAddress(address)
            .setPassword(password);
        
        config.setCodec(new FastJsonRedissonCodec());
        
        return Redisson.create(config);
    }
    
    @Bean
    public ThreadPoolExecutorRegistry registry(
        RedissonClient redissonClient
    ) {
        RedisThreadPoolExecutorRegistry redisThreadPoolExecutorRegistry = new RedisThreadPoolExecutorRegistry(redissonClient);
        
        return redisThreadPoolExecutorRegistry;
    }
    
    @Bean
    public ThreadPoolExecutorService threadPoolExecutorService(
        ApplicationContext applicationContext,
        Map<String, ThreadPoolExecutor> threadPoolExecutorMap,
        RedissonClient redissonClient
    ) {
        String applicationName = applicationContext.getEnvironment().getProperty("spring.application.name");
        
        if (StrUtil.isBlank(applicationName)) {
            throw new RuntimeException("Not found the application name");
        }
        
        for (Map.Entry<String, ThreadPoolExecutor> threadPoolExecutorEntry : threadPoolExecutorMap.entrySet()) {
            String threadPoolName = threadPoolExecutorEntry.getKey();
            ThreadPoolExecutorProp threadPoolExecutorProp
                = redissonClient.<ThreadPoolExecutorProp>getBucket(ThreaticCacheKey.TREAD_POOL_EXECUTOR_PROP.getKey(applicationName, threadPoolName)).get();
            if (threadPoolExecutorProp == null) {
                continue;
            }
            
            ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorEntry.getValue();
            threadPoolExecutor.setCorePoolSize(threadPoolExecutorProp.getCorePoolSize());
            threadPoolExecutor.setMaximumPoolSize(threadPoolExecutorProp.getMaximumPoolSize());
        }
        
        ThreadPoolExecutorServiceImpl threadPoolService = new ThreadPoolExecutorServiceImpl(applicationName, threadPoolExecutorMap);
        
        return threadPoolService;
    }
    
    @Bean
    public ThreadPoolExecutorPropReportJob threadPoolExecutorPropReportJob(
        ThreadPoolExecutorService threadPoolExecutorService,
        ThreadPoolExecutorRegistry threadPoolExecutorRegistry
    ) {
        ThreadPoolExecutorPropReportJob threadPoolExecutorPropReportJob
            = new ThreadPoolExecutorPropReportJob(threadPoolExecutorService, threadPoolExecutorRegistry);
        
        return threadPoolExecutorPropReportJob;
    }
    
    @Bean
    public ThreadPoolExecutorPropUpdateMessageListener threadPoolExecutorPropUpdateMessageListener(
        ThreadPoolExecutorService threadPoolExecutorService,
        ThreadPoolExecutorRegistry threadPoolExecutorRegistry
    ) {
        ThreadPoolExecutorPropUpdateMessageListener threadPoolExecutorPropUpdateMessageListener
            = new ThreadPoolExecutorPropUpdateMessageListener(threadPoolExecutorService, threadPoolExecutorRegistry);
        
        return threadPoolExecutorPropUpdateMessageListener;
    }
    
    @Bean(name = "threadPoolExecutorPropUpdateTopic")
    public RTopic threadPoolExecutorPropUpdateTopic(
        ApplicationContext applicationContext,
        RedissonClient redissonClient,
        ThreadPoolExecutorPropUpdateMessageListener threadPoolExecutorPropUpdateMessageListener
    ) {
        String applicationName = applicationContext.getEnvironment().getProperty("spring.application.name");
        RTopic threadPoolExecutorPropUpdateTopic = redissonClient.getTopic(ThreaticCacheKey.TREAD_POOL_EXECUTOR_PROP_TOPIC.getKey(applicationName));
        threadPoolExecutorPropUpdateTopic.addListener(ThreadPoolExecutorUpdateMessage.class, threadPoolExecutorPropUpdateMessageListener);
        
        return threadPoolExecutorPropUpdateTopic;
    }
}
