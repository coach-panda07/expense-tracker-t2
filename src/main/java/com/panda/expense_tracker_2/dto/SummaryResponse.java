package com.panda.expense_tracker_2.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class SummaryResponse {
    BigDecimal totalExpenditure;
    BigDecimal income;
    BigDecimal difference;


}
