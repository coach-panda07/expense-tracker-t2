package com.panda.expense_tracker_2.controller;

import com.panda.expense_tracker_2.exception.NotFoundException;
import com.panda.expense_tracker_2.service.SummaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SummaryController.class)
@AutoConfigureMockMvc(addFilters = false)
class SummaryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SummaryService summaryService;

    @Test
    void summaryForLogbookWithoutIncomeReturns404() throws Exception{
        when(summaryService.summary(1L))
                .thenThrow(new NotFoundException("Income not found"));
        mockMvc.perform(get("/logbooks/{logbookId}/summary", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Income not found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.timestamp").exists());

    }

    @Test
    void summaryWithInvalidLogbookIdReturns400() throws Exception {
        mockMvc.perform(get("/logbooks/{logbookId}/summary", "abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid value 'abc' for parameter 'logbookId'"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void summaryUnexpectedErrorReturns500() throws Exception {
        when(summaryService.summary(1L))
                .thenThrow(new RuntimeException("boom"));
        mockMvc.perform(get("/logbooks/{logbookId}/summary", 1L))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
