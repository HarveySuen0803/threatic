package com.harvey.threatic.starter.domain.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-10
 */
@Data
public class ThreadPoolExecutorUpdateMessage implements Serializable {
    private String applicationName;
    
    private String threadPoolName;
    
    private int corePoolSize;
    
    private int maximumPoolSize;
    
    @Serial
    private static final long serialVersionUID = 1L;
}
