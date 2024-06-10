package com.harvey.threatic.dashboard.controller;

import com.harvey.common.constant.Result;
import com.harvey.threatic.starter.domain.model.eo.ThreadPoolExecutorProp;
import com.harvey.threatic.starter.domain.model.vo.ThreaticCacheKey;
import jakarta.annotation.Resource;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-10
 */
@RestController
public class ThreaticDashboardController {
    @Resource
    private RedissonClient redissonClient;
    
    @GetMapping("/api/v1/threatic/dashboard/list")
    public Result<List<ThreadPoolExecutorProp>> listThreadPoolExecutorProp() {
        RList<ThreadPoolExecutorProp> rlist = redissonClient.getList(ThreaticCacheKey.TREAD_POOL_EXECUTOR_PROP_LIST.getKey());
        List<ThreadPoolExecutorProp> threadPoolExecutorPropList = rlist.readAll();
        return Result.success(threadPoolExecutorPropList);
    }
}
