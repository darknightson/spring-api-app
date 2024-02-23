package com.app.api.feigntest.client;

import com.app.api.health.dto.HealthCheckResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "http://localhost:8081", name = "helloClient")
public interface HelloClient {

    @GetMapping(value = "/api/health-check", consumes = "application/json")
    HealthCheckResponseDto healthCheck();
}
