package com.app.api.health.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HealthCheckResponseDto {

    private String health;
    private List<String> activeProfiles;
}
