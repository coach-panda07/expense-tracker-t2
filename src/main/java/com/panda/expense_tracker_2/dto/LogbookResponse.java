package com.panda.expense_tracker_2.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LogbookResponse {
    private Long id;
    private String name;
    private BigDecimal incomeAmount;
    private LocalDate incomeDate;

    public LogbookResponse(Long id, String name, BigDecimal incomeAmount, LocalDate incomeDate){
        this.name = name;
        this.id = id;
        this.incomeAmount = incomeAmount;
        this.incomeDate = incomeDate;
    }

}
