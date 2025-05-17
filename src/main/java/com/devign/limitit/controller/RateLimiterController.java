package com.devign.limitit.controller;

import com.devign.limitit.service.RateLimiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rate-limit")
@RequiredArgsConstructor
public class RateLimiterController {

    private final RateLimiterService rateLimiterService;

    @GetMapping("/{userId}")
    public ResponseEntity<String> checkRateLimit(@PathVariable String userId) {
        if (rateLimiterService.isAllowed(userId)) {
            return ResponseEntity.ok("Request Allowed");
        } else {
            return ResponseEntity.status(429).body("Rate limit exceeded");
        }
    }
}
