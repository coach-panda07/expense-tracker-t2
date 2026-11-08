package com.panda.expense_tracker_2.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timestamp;
}
