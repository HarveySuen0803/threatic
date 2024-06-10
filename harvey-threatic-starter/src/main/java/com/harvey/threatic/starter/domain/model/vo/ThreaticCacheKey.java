package com.harvey.threatic.starter.domain.model.vo;

import java.util.concurrent.TimeUnit;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-09
 */
public class ThreaticCacheKey extends CacheKey {
    public static final CacheKey TREAD_POOL_EXECUTOR_PROP = new CacheKey("threatic:threadPoolExecutorProp:one:%s:%s", 60 * 60 * 24 * 7L, TimeUnit.SECONDS);
    
    public static final CacheKey TREAD_POOL_EXECUTOR_PROP_LIST = new CacheKey("threatic:threadPoolExecutorProp:list", 60 * 60 * 24 * 7L, TimeUnit.SECONDS);
    
    public static final CacheKey TREAD_POOL_EXECUTOR_PROP_TOPIC = new CacheKey("threatic:threadPoolExecutorProp:topic:%s", 60 * 60 * 24 * 7L, TimeUnit.SECONDS);
}
