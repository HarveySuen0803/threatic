package com.harvey.threatic.starter.registry;

import com.harvey.threatic.starter.domain.model.eo.ThreadPoolExecutorProp;

import java.util.List;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-07
 */
public interface ThreadPoolExecutorRegistry {
    void reportThreadPoolExecutorProp(ThreadPoolExecutorProp threadPoolExecutorProp);
    
    void reportThreadPoolExecutorPropList(List<ThreadPoolExecutorProp> threadPoolExecutorPropList);
}
