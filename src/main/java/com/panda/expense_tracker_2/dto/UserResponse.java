package com.panda.expense_tracker_2.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UserResponse {
    private Long id;

    private String email;

    private LocalDateTime createdAt;
}
