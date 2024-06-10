package com.harvey.threatic.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-09
 */
@Data
@ConfigurationProperties(prefix = "threatic.redisson", ignoreInvalidFields = true)
public class ThreaticRedissonProperties {
    private String host = "127.0.0.1";
    
    private int port = 6379;
    
    private String password = "111";
}
