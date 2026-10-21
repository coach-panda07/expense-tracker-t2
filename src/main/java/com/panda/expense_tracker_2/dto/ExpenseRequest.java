package com.panda.expense_tracker_2.dto;

import com.panda.expense_tracker_2.model.Category;
import com.panda.expense_tracker_2.model.Logbook;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseRequest {
    private String name;
    @Positive
    private BigDecimal cost;
    private LocalDate date;
    private Long categoryId;
}
