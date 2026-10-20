package com.panda.expense_tracker_2.service;

import com.panda.expense_tracker_2.dto.ExpenseRequest;
import com.panda.expense_tracker_2.dto.ExpenseResponse;
import com.panda.expense_tracker_2.model.Category;
import com.panda.expense_tracker_2.model.Expense;
import com.panda.expense_tracker_2.model.Logbook;
import com.panda.expense_tracker_2.repository.CategoryRepository;
import com.panda.expense_tracker_2.repository.ExpenseRepository;
import com.panda.expense_tracker_2.repository.LogbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final LogbookRepository logbookRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseResponse logExpense(Long logbookId, ExpenseRequest request) {
        Category categoryFound = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new RuntimeException("category not found"));
        if (!categoryFound.isActive()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"category is not active");
        }
        Logbook logbookFound = logbookRepository.findById(logbookId).orElseThrow(() -> new RuntimeException("logbook not found"));
        Expense expense = expenseRepository.save(new Expense(request.getName(), request.getCost(), request.getDate(), categoryFound, logbookFound));

        return new ExpenseResponse(expense.getId(), expense.getName(), expense.getCost(), expense.getDate(), expense.getCategory().getId());
    }

    public List<ExpenseResponse> getExpenses(Long logbookId) {
        Logbook logbookFound = logbookRepository.findById(logbookId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Logbook not found"));
        return expenseRepository.findByLogbookId(logbookId).stream()
                .map(e -> new ExpenseResponse(e.getId(), e.getName(), e.getCost(), e.getDate(), e.getCategory().getId()))
                .toList();
    }

    public ExpenseResponse deleteExpense(Long expenseId) {
        Expense found = expenseRepository
                .findById(expenseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "expense not found"));
        ExpenseResponse response = new ExpenseResponse(found.getId(), found.getName(), found.getCost(), found.getDate(), found.getCategory().getId());
        expenseRepository.delete(found);
        return response;
    }

}