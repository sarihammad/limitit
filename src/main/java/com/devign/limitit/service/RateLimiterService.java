package com.devign.limitit.service;

import com.devign.limitit.entity.RateLimit;
import com.devign.limitit.repository.RateLimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final RateLimitRepository rateLimitRepository;
    private static final long LIMIT = 100; // 100 reqs / min
    private static final long EXPIRY = 60; // 60 sec window

    public boolean isAllowed(String userId) {
        RateLimit rateLimit = rateLimitRepository.findByUserId(userId)
                .orElseGet(() -> createNewRateLimit(userId));

        if (rateLimit.getExpiryTime().isBefore(LocalDateTime.now())) {
            //Reset limit
            rateLimit.setRequestCount(1L);
            rateLimit.setExpiryTime(LocalDateTime.now().plus(EXPIRY, ChronoUnit.SECONDS));
        } else {
            rateLimit.setRequestCount(rateLimit.getRequestCount() + 1);
        }

        rateLimitRepository.save(rateLimit);
        return rateLimit.getRequestCount() <= LIMIT;
    }

    private RateLimit createNewRateLimit(String userId) {
        RateLimit newLimit = new RateLimit();
        newLimit.setUserId(userId);
        newLimit.setRequestCount(1L);
        newLimit.setExpiryTime(LocalDateTime.now().plus(EXPIRY, ChronoUnit.SECONDS));
        return rateLimitRepository.save(newLimit);
    }
}