package com.panda.expense_tracker_2.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SummaryResponse {
    private BigDecimal totalExpenditure;
    private BigDecimal income;
    private BigDecimal difference;

    public SummaryResponse(BigDecimal totalExpenditure,
                           BigDecimal income,
                           BigDecimal difference) {
        this.difference = difference;
        this.income = income;
        this.totalExpenditure = totalExpenditure;
    }
}
