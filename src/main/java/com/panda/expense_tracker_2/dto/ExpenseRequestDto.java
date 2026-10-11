package com.panda.expense_tracker_2.dto;

import com.panda.expense_tracker_2.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseRequestDto {
    private Long id;
    private String name;
    private BigDecimal cost;
    private LocalDate date;
    private Long categoryId;
    private Long logbookId;
}
