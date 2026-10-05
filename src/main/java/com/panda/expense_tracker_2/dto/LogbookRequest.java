package com.panda.expense_tracker_2.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LogbookRequest {
    private Long id;
    private String name;
    private BigDecimal incomeAmount;
    private LocalDate incomeDate;
}
