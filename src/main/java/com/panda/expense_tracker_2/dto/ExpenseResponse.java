package com.panda.expense_tracker_2.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseResponseDto {
    private String name;
    private BigDecimal cost;
    private LocalDate date;
    private Long categoryId;
}
