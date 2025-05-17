package com.devign.limitit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "rate_limits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private Long requestCount;

    @Column(nullable = false)
    private LocalDateTime expiryTime;

}