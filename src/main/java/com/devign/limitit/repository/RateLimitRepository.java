package com.devign.limitit.repository;

import com.devign.limitit.entity.RateLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateLimitRepository extends JpaRepository<RateLimit, Long> {
    Optional<RateLimit> findByUserId(String userId);
}