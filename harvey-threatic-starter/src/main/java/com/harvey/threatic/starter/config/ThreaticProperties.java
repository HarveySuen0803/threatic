package com.harvey.threatic.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-09
 */
@Data
@ConfigurationProperties(prefix = "threatic", ignoreInvalidFields = true)
public class ThreaticProperties {
    private boolean isEnabled;
    
    private int poolSize = 1;
    
    private int minIdleSize = 3;
    
    private int idleTimeout = 10000;
    
    private int connectTimeout = 10000;
    
    private int retryAttempts = 3;
    
    private int retryInterval = 1000;
    
    private int pingInterval = 0;
    
    private boolean keepAlive = true;
}
