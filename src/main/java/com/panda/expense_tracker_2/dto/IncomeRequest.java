package com.panda.expense_tracker_2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class IncomeRequest {

    @NotNull
    @Positive
    private BigDecimal amount;

    private LocalDate date;

}
