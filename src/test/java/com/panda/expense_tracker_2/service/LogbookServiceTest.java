package com.panda.expense_tracker_2.service;

import com.panda.expense_tracker_2.dto.LogbookResponse;
import com.panda.expense_tracker_2.model.Logbook;
import com.panda.expense_tracker_2.repository.IncomeRepository;
import com.panda.expense_tracker_2.repository.LogbookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogbookServiceTest {
    @Mock
    private LogbookRepository logbookRepository;
    @Mock
    private IncomeRepository incomeRepository;
    @InjectMocks
    private LogbookService logbookService;

    @Test
    void deleteLogbookWithoutIncomeReturnsNullIncomeFields() {
        Logbook logbook = new Logbook("Trip");
        logbook.setId(1L);
        when(logbookRepository.findById(1L)).thenReturn(Optional.of(logbook));
        when(incomeRepository.findByLogbookId(1L)).thenReturn(Optional.empty());

        LogbookResponse response = logbookService.deleteLogbook(1L);

        assertEquals(1L, response.getId());
        assertEquals("Trip", response.getName());
        assertNull(response.getIncomeAmount());
        assertNull(response.getIncomeDate());
        verify(logbookRepository).delete(logbook);
    }
}
