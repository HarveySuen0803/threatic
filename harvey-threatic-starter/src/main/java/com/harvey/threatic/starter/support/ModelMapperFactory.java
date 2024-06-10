package com.harvey.threatic.starter.support;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-10
 */
public class ModelMapperFactory {
    private static volatile ModelMapper modelMapper;
    
    // Private constructor to prevent instantiation.
    private ModelMapperFactory() {
    }
    
    public static ModelMapper getModelMapper() {
        if (modelMapper != null) {
            return modelMapper;
        }
        
        synchronized (ModelMapperFactory.class) {
            if (modelMapper != null) {
                return modelMapper;
            }
            
            initModelMapper();
        }
        
        return modelMapper;
    }
    
    private static void initModelMapper() {
        modelMapper = new ModelMapper();
        
        modelMapper.getConfiguration()
            .setFieldMatchingEnabled(true)
            .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }
}
