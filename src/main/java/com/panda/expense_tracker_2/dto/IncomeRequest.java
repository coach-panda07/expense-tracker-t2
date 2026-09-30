package com.panda.expense_tracker_2.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class IncomeRequest {

    private Long id;

    private BigDecimal amount;

    private LocalDate date;

}
