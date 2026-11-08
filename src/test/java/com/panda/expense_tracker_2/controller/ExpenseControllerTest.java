package com.panda.expense_tracker_2.controller;

import com.panda.expense_tracker_2.dto.ExpenseRequest;
import com.panda.expense_tracker_2.service.ExpenseService;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseController.class)
@AutoConfigureMockMvc(addFilters = false)
class ExpenseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExpenseService expenseservice;

    @Test
    void ExpenseWithoutDate() throws Exception {
        mockMvc.perform(post("/logbooks/{logbookId}/expenses", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Lunch",
                            "cost": 12.58,
                            "categoryId": 1
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("[date: must not be null]"))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.timestamp").exists());

    }

    @Test
    void ExpenseWithoutCategoryId() throws Exception {
        mockMvc.perform(post("/logbooks/{logbookId}/expenses", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Lunch",
                            "cost": 12.58,
                            "date": "2026-11-08"
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("[categoryId: must not be null]"))
                .andExpect(jsonPath("$.status").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.timestamp").exists());

    }
}
