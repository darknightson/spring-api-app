package com.app.api.exception.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ExceptionTestDto {

    @NotBlank(message = "username is mandatory")
    private String username;

    @Max(value = 10, message = "age must be less than or equal to 10")
    private int age;

}
