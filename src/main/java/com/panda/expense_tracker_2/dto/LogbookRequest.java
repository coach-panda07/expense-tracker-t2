package com.panda.expense_tracker_2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class LogbookRequest {

    @NotBlank
    String name;

    @NotNull
    @Positive
    BigDecimal incomeAmount;
    LocalDate incomeDate;
}
