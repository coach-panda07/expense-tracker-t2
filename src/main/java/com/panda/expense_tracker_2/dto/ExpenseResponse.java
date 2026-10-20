package com.panda.expense_tracker_2.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseResponse {
    private Long id;
    private String name;
    private BigDecimal cost;
    private LocalDate date;
    private Long categoryId;

    public ExpenseResponse(Long id, String name, BigDecimal cost, LocalDate date, Long categoryId) {
        this.name = name;
        this.id = id;
        this.cost = cost;
        this.date = date;
        this.categoryId = categoryId;
    }
}

