package com.panda.expense_tracker_2.service;

import com.panda.expense_tracker_2.dto.SummaryResponse;
import com.panda.expense_tracker_2.exception.NotFoundException;
import com.panda.expense_tracker_2.repository.ExpenseRepository;
import com.panda.expense_tracker_2.repository.IncomeRepository;
import com.panda.expense_tracker_2.repository.LogbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class SummaryService {
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final LogbookRepository logbookRepository;

    public SummaryResponse summary(Long logbookId){
         logbookRepository
                .findById(logbookId)
                .orElseThrow(()-> new NotFoundException("Logbook not found"));

        BigDecimal sum = expenseRepository.sumCostByLogbookId(logbookId);
        BigDecimal income = incomeRepository.findByLogbookId(logbookId).get().getAmount();
        BigDecimal difference = income.subtract(sum);

        return new SummaryResponse(sum, income, difference);
    }

}
