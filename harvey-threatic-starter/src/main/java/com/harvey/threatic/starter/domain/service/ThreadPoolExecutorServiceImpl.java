package com.harvey.threatic.starter.domain.service;

import cn.hutool.core.util.StrUtil;
import com.harvey.threatic.starter.domain.model.dto.ThreadPoolExecutorSetDto;
import com.harvey.threatic.starter.domain.model.dto.ThreadPoolExecutorUpdateMessage;
import com.harvey.threatic.starter.domain.model.eo.ThreadPoolExecutorProp;
import com.harvey.threatic.starter.support.ModelMapperFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-07
 */
public class ThreadPoolExecutorServiceImpl implements ThreadPoolExecutorService {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolExecutorServiceImpl.class);
    
    private static final ModelMapper modelMapper = ModelMapperFactory.getModelMapper();
    
    private final String applicationName;
    
    private final Map<String, ThreadPoolExecutor> threadPoolExecutorMap;
    
    public ThreadPoolExecutorServiceImpl(
        String applicationName,
        Map<String, ThreadPoolExecutor> threadPoolExecutorMap
    ) {
        this.applicationName = applicationName;
        this.threadPoolExecutorMap = threadPoolExecutorMap;
    }
    
    @Override
    public List<ThreadPoolExecutorProp> getThreadPoolExecutorPropList() {
        logger.info("Get parameter information of the thread pool list.");
        
        Set<String> threadPoolNameSet = threadPoolExecutorMap.keySet();
        
        List<ThreadPoolExecutorProp> threadPoolExecutorPropList = new ArrayList<>(threadPoolExecutorMap.size());
        
        for (String threadPoolName : threadPoolNameSet) {
            ThreadPoolExecutorProp threadPoolExecutorProp = getThreadPoolExecutorProp(threadPoolName);
            
            threadPoolExecutorPropList.add(threadPoolExecutorProp);
        }
        
        return threadPoolExecutorPropList;
    }
    
    @Override
    public ThreadPoolExecutorProp getThreadPoolExecutorProp(String threadPoolName) {
        logger.info("Get parameter information of the thread pool, ThreadPoolName: {}.", threadPoolName);
        
        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorMap.get(threadPoolName);
        
        ThreadPoolExecutorProp threadPoolExecutorProp = new ThreadPoolExecutorProp();
        threadPoolExecutorProp.setApplicationName(applicationName);
        threadPoolExecutorProp.setThreadPoolName(threadPoolName);
        threadPoolExecutorProp.setCorePoolSize(threadPoolExecutor.getCorePoolSize());
        threadPoolExecutorProp.setMaximumPoolSize(threadPoolExecutor.getMaximumPoolSize());
        threadPoolExecutorProp.setActiveCount(threadPoolExecutor.getActiveCount());
        threadPoolExecutorProp.setQueueType(threadPoolExecutor.getQueue().getClass().getSimpleName());
        threadPoolExecutorProp.setQueueSize(threadPoolExecutor.getQueue().size());
        threadPoolExecutorProp.setRemainingCapacity(threadPoolExecutor.getQueue().remainingCapacity());
        
        return threadPoolExecutorProp;
    }
    
    @Override
    public ThreadPoolExecutor getThreadPoolExecutor(String threadPoolName) {
        logger.info("Get thread pool, ThreadPoolName: {}", threadPoolName);
        
        if (!StrUtil.isBlank(threadPoolName)) {
            throw new RuntimeException("The thread pool name is invalid");
        }
        
        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorMap.get(threadPoolName);
        if (threadPoolExecutor == null) {
            throw new RuntimeException("Not found the thread pool");
        }
        
        return threadPoolExecutor;
    }
    
    @Override
    public void setThreadPoolExecutor(ThreadPoolExecutorSetDto threadPoolExecutorSetDto) {
        logger.info("Set thread pool parameter information, ThreadPoolExecutorProp: {}", threadPoolExecutorSetDto);
        
        if (!StrUtil.equals(applicationName, threadPoolExecutorSetDto.getApplicationName())) {
            throw new RuntimeException("The application name is invalid");
        }
        
        String threadPoolName = threadPoolExecutorSetDto.getThreadPoolName();
        if (StrUtil.isBlank(threadPoolName)) {
            throw new RuntimeException("The thread pool name is invalid");
        }
        
        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorMap.get(threadPoolName);
        if (threadPoolExecutor == null) {
            throw new RuntimeException("Not found the thread pool");
        }
        
        threadPoolExecutor.setCorePoolSize(threadPoolExecutorSetDto.getCorePoolSize());
        threadPoolExecutor.setMaximumPoolSize(threadPoolExecutorSetDto.getMaximumPoolSize());
    }
    
    @Override
    public ThreadPoolExecutorSetDto buildThreadPoolExecutorSetDto(ThreadPoolExecutorUpdateMessage threadPoolExecutorUpdateMessage) {
        ThreadPoolExecutorSetDto threadPoolExecutorSetDto = modelMapper.map(threadPoolExecutorUpdateMessage, ThreadPoolExecutorSetDto.class);
        
        return threadPoolExecutorSetDto;
    }
}
