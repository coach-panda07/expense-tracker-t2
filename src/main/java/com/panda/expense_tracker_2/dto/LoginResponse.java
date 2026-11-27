package com.panda.expense_tracker_2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class LoginResponse {
    @NotBlank
    private String accessToken;
}
