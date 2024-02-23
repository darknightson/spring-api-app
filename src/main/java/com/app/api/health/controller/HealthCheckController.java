package com.app.api.health.controller;

import com.app.api.health.dto.HealthCheckResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HealthCheckController {

    private final Environment environment;

    @GetMapping("/health-check")
    public ResponseEntity<HealthCheckResponseDto> healthCheck() {
        log.info("activeProfiles={}",environment.getActiveProfiles());
        return ResponseEntity.ok(HealthCheckResponseDto.builder()
                .health("ok")
                .activeProfiles(List.of(environment.getActiveProfiles()))
                .build());
    }
}
