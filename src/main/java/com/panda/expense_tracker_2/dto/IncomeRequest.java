package com.panda.expense_tracker_2.dto;

import com.panda.expense_tracker_2.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class IncomeRequest {

    private Long id;

    private String name;

    private BigDecimal cost;

}
