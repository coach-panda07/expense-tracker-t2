package com.panda.expense_tracker_2.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UserResponse {
    @Id
    private Long id;

    @NotBlank
    private String email;

    private LocalDateTime createdAt;
}
