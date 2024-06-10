package com.harvey.threatic.starter.domain.model.eo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-07
 */
@Data
public class ThreadPoolExecutorProp implements Serializable {
    private String applicationName;
    
    private String threadPoolName;
    
    private int corePoolSize;
    
    private int maximumPoolSize;
    
    private int activeCount;
    
    private String queueType;
    
    private int queueSize;
    
    private int remainingCapacity;
    
    @Serial
    private static final long serialVersionUID = 1L;
}
