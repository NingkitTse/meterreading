package com.wasion.meterreading.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class IpAccessCacheService {

    private static final String PREFIX = "REQUEST_IP_LIMIT:";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void add(String key, String value, TimeUnit timeUnit, long t) {
        redisTemplate.opsForValue().set(PREFIX + key, value, t, timeUnit);
    }

    public int count(String key) {
        return redisTemplate.keys(PREFIX + key + "*").size();
    }

}
