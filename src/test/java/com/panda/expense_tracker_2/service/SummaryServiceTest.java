package com.panda.expense_tracker_2.service;

import com.panda.expense_tracker_2.exception.NotFoundException;
import com.panda.expense_tracker_2.model.Logbook;
import com.panda.expense_tracker_2.repository.ExpenseRepository;
import com.panda.expense_tracker_2.repository.IncomeRepository;
import com.panda.expense_tracker_2.repository.LogbookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SummaryServiceTest {
    @Mock
    private LogbookRepository logbookRepository;
    @Mock
    private IncomeRepository incomeRepository;
    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private SummaryService summaryService;

    @Test
    void summaryThrowsNotFoundWhenLogbookHasNoIncome(){
       when(logbookRepository.findById(1L)).thenReturn(Optional.of(new Logbook("Trip")));
       when(expenseRepository.sumCostByLogbookId(1L)).thenReturn(BigDecimal.ZERO);
       when(incomeRepository.findByLogbookId(1L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class,
                ()-> summaryService.summary(1L));

        assertEquals("Income not found", ex.getMessage());
    }

}
