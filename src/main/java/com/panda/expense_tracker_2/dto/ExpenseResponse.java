package com.panda.expense_tracker_2.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class ExpenseResponse {
    Long id;
    String name;
    BigDecimal cost;
    LocalDate date;
    Long categoryId;


}

