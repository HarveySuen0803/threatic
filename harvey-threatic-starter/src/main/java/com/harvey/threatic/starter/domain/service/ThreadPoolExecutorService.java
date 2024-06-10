package com.harvey.threatic.starter.domain.service;

import com.harvey.threatic.starter.domain.model.dto.ThreadPoolExecutorSetDto;
import com.harvey.threatic.starter.domain.model.dto.ThreadPoolExecutorUpdateMessage;
import com.harvey.threatic.starter.domain.model.eo.ThreadPoolExecutorProp;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-07
 */
public interface ThreadPoolExecutorService {
    List<ThreadPoolExecutorProp> getThreadPoolExecutorPropList();
    
    ThreadPoolExecutorProp getThreadPoolExecutorProp(String threadPoolName);
    
    ThreadPoolExecutor getThreadPoolExecutor(String threadPoolName);
    
    void setThreadPoolExecutor(ThreadPoolExecutorSetDto threadPoolExecutorSetDto);
    
    ThreadPoolExecutorSetDto buildThreadPoolExecutorSetDto(ThreadPoolExecutorUpdateMessage threadPoolExecutorUpdateMessage);
}
