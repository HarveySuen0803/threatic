package com.harvey.threatic.starter.trigger.job;

import com.harvey.threatic.starter.domain.model.eo.ThreadPoolExecutorProp;
import com.harvey.threatic.starter.domain.service.ThreadPoolExecutorService;
import com.harvey.threatic.starter.registry.ThreadPoolExecutorRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-09
 */
public class ThreadPoolExecutorPropReportJob {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolExecutorPropReportJob.class);
    private final ThreadPoolExecutorService threadPoolExecutorService;
    
    private final ThreadPoolExecutorRegistry threadPoolExecutorRegistry;
    
    public ThreadPoolExecutorPropReportJob(
        ThreadPoolExecutorService threadPoolExecutorService,
        ThreadPoolExecutorRegistry threadPoolExecutorRegistry
    ) {
        this.threadPoolExecutorService = threadPoolExecutorService;
        this.threadPoolExecutorRegistry = threadPoolExecutorRegistry;
    }
    
    @Scheduled(cron = "0/30 * * * * ?")
    public void reportThreadPoolExecutorPropList() {
        logger.info("Report the thread pool parameters to the registry.");
        
        List<ThreadPoolExecutorProp> threadPoolExecutorPropList = threadPoolExecutorService.getThreadPoolExecutorPropList();
        
        threadPoolExecutorRegistry.reportThreadPoolExecutorPropList(threadPoolExecutorPropList);
        
        for (ThreadPoolExecutorProp threadPoolExecutorProp : threadPoolExecutorPropList) {
            threadPoolExecutorRegistry.reportThreadPoolExecutorProp(threadPoolExecutorProp);
        }
    }
}
