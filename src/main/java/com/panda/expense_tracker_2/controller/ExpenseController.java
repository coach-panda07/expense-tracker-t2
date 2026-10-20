package com.panda.expense_tracker_2.controller;

import com.panda.expense_tracker_2.dto.ExpenseRequest;
import com.panda.expense_tracker_2.dto.ExpenseResponse;
import com.panda.expense_tracker_2.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping("/logbooks/{logbookId}/expenses")
    public ResponseEntity<ExpenseResponse> createExpense(
            @Valid
            @RequestBody
            ExpenseRequest request,

            @Valid
            @PathVariable
            Long logbookId
    ){
        return new ResponseEntity<ExpenseResponse>(expenseService.logExpense(logbookId,request),
                HttpStatus.CREATED);
    }


    @GetMapping("/logbooks/{logbookId}/expenses")
    public ResponseEntity<List<ExpenseResponse>> getExpenses(
            @Valid
            @PathVariable
            Long logbookId
    ){
        return new ResponseEntity<List<ExpenseResponse>>(expenseService.getExpenses(logbookId),
                HttpStatus.OK);
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<ExpenseResponse> deleteExpense(
            @Valid
            @PathVariable
            Long id
    ){
        return new ResponseEntity<ExpenseResponse>(expenseService.deleteExpense(id),
                HttpStatus.OK);
    }
}
