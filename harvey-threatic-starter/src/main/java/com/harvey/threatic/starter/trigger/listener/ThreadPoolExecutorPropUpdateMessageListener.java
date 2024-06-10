package com.harvey.threatic.starter.trigger.listener;

import com.harvey.threatic.starter.domain.model.dto.ThreadPoolExecutorSetDto;
import com.harvey.threatic.starter.domain.model.dto.ThreadPoolExecutorUpdateMessage;
import com.harvey.threatic.starter.domain.model.eo.ThreadPoolExecutorProp;
import com.harvey.threatic.starter.domain.service.ThreadPoolExecutorService;
import com.harvey.threatic.starter.registry.ThreadPoolExecutorRegistry;
import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-10
 */
public class ThreadPoolExecutorPropUpdateMessageListener implements MessageListener<ThreadPoolExecutorUpdateMessage> {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolExecutorPropUpdateMessageListener.class);
    
    private final ThreadPoolExecutorService threadPoolExecutorService;
    
    private final ThreadPoolExecutorRegistry threadPoolExecutorRegistry;
    
    public ThreadPoolExecutorPropUpdateMessageListener(
        ThreadPoolExecutorService threadPoolExecutorService,
        ThreadPoolExecutorRegistry threadPoolExecutorRegistry
    ) {
        this.threadPoolExecutorService = threadPoolExecutorService;
        this.threadPoolExecutorRegistry = threadPoolExecutorRegistry;
    }
    
    @Override
    public void onMessage(CharSequence channel, ThreadPoolExecutorUpdateMessage threadPoolExecutorUpdateMessage) {
        logger.info(
            "Parameters of the thread pool have changed, ThreadPoolName: {}, CorePoolSize: {}, MaximumPoolSize: {}.",
            threadPoolExecutorUpdateMessage.getThreadPoolName(),
            threadPoolExecutorUpdateMessage.getCorePoolSize(),
            threadPoolExecutorUpdateMessage.getMaximumPoolSize()
        );
        
        ThreadPoolExecutorSetDto threadPoolExecutorSetDto = threadPoolExecutorService.buildThreadPoolExecutorSetDto(threadPoolExecutorUpdateMessage);
        threadPoolExecutorService.setThreadPoolExecutor(threadPoolExecutorSetDto);
        
        List<ThreadPoolExecutorProp> threadPoolExecutorPropList = threadPoolExecutorService.getThreadPoolExecutorPropList();
        threadPoolExecutorRegistry.reportThreadPoolExecutorPropList(threadPoolExecutorPropList);
        
        String threadPoolName = threadPoolExecutorUpdateMessage.getThreadPoolName();
        ThreadPoolExecutorProp threadPoolExecutorProp = threadPoolExecutorService.getThreadPoolExecutorProp(threadPoolName);
        threadPoolExecutorRegistry.reportThreadPoolExecutorProp(threadPoolExecutorProp);
    }
}
