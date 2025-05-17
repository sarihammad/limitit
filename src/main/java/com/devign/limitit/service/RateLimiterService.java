package com.devign.limitit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final long LIMIT = 100; // 100 reqs / min
    private static final long EXPIRY = 60; // 60 sec window

    public boolean isAllowed(String key) {
        Long current = redisTemplate.opsForValue().increment(key);
        
        if (current == 1) {
            redisTemplate.expire(key, Duration.ofSeconds(EXPIRY));
        }

        return current <= LIMIT;
    }
}