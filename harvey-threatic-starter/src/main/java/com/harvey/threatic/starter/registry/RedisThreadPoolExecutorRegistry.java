package com.harvey.threatic.starter.registry;

import cn.hutool.core.collection.CollUtil;
import com.harvey.threatic.starter.domain.model.eo.ThreadPoolExecutorProp;
import com.harvey.threatic.starter.domain.model.vo.ThreaticCacheKey;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-07
 */
public class RedisThreadPoolExecutorRegistry implements ThreadPoolExecutorRegistry {
    private static final Logger logger = LoggerFactory.getLogger(RedisThreadPoolExecutorRegistry.class);
    
    private final RedissonClient redissonClient;
    
    public RedisThreadPoolExecutorRegistry(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
    
    @Override
    public void reportThreadPoolExecutorProp(ThreadPoolExecutorProp threadPoolExecutorProp) {
        logger.info("Report the parameter information of the thread pool, ThreadPoolExecutorProp: {}.", threadPoolExecutorProp);
        
        if (threadPoolExecutorProp == null) {
            throw new RuntimeException("The threadPoolExecutorProp is null");
        }
        
        String applicationName = threadPoolExecutorProp.getApplicationName();
        String threadPoolName = threadPoolExecutorProp.getThreadPoolName();
        
        // Get the bucket of ThreadPoolExecutorProp, then set the new ThreadPoolExecutorProp to the bucket.
        RBucket<ThreadPoolExecutorProp> rbucket = redissonClient.getBucket(ThreaticCacheKey.TREAD_POOL_EXECUTOR_PROP.getKey(applicationName, threadPoolName));
        rbucket.set(threadPoolExecutorProp, ThreaticCacheKey.TREAD_POOL_EXECUTOR_PROP.timeout, ThreaticCacheKey.TREAD_POOL_EXECUTOR_PROP.unit);
    }
    
    @Override
    public void reportThreadPoolExecutorPropList(List<ThreadPoolExecutorProp> threadPoolExecutorPropList) {
        logger.info("Report the parameter information of the thread pool, ThreadPoolExecutorPropList: {}.", threadPoolExecutorPropList);
        
        if (CollUtil.isEmpty(threadPoolExecutorPropList)) {
            throw new RuntimeException("The threadPoolExecutorPropList is blank");
        }
        
        // Clear the ThreadPoolExecutorPropList in the registry center, then store the new ThreadPoolExecutorPropList.
        RList<ThreadPoolExecutorProp> rlist = redissonClient.getList(ThreaticCacheKey.TREAD_POOL_EXECUTOR_PROP_LIST.getKey());
        rlist.delete();
        rlist.addAll(threadPoolExecutorPropList);
    }
}
