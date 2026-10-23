package com.panda.expense_tracker_2.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class LogbookResponse {
    Long id;
    String name;
    BigDecimal incomeAmount;
    LocalDate incomeDate;



}
