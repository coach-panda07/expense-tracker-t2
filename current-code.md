# Expense Tracker T2 — Current Code

Snapshot synced to the source files on disk: models, DTOs, repositories,
exception hanadlers, services, controllers, and the application/test classes.

## Category.java

`src/main/java/com/panda/expense_tracker_2/model/Category.java`

```java
package com.panda.expense_tracker_2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Category")
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Column
    private boolean active = true;

    public Category(String name) {
        this.name = name;
    }

}
```

## Income.java

`src/main/java/com/panda/expense_tracker_2/model/Income.java`

```java
package com.panda.expense_tracker_2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Income")
@NoArgsConstructor
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @OneToOne
    @JoinColumn(name = "logbook_id", nullable = false)
    private Logbook logbook;

    public Income(LocalDate date, BigDecimal amount, Logbook logbook) {
        this.date = date;
        this.amount = amount;
        this.logbook = logbook;
    }

}
```

## Expense.java

`src/main/java/com/panda/expense_tracker_2/model/Expense.java`

```java
package com.panda.expense_tracker_2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Expense")
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal cost;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logbook_id")
    private Logbook logbook;


    public Expense(String name, BigDecimal cost, LocalDate date, Category category, Logbook logbook) {
        this.name = name;
        this.cost = cost;
        this.date = date;
        this.category = category;
        this.logbook = logbook;
    }

}
```

## Logbook.java

`src/main/java/com/panda/expense_tracker_2/model/Logbook.java`

```java
package com.panda.expense_tracker_2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Log_Book")
@NoArgsConstructor
public class Logbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "logbook", cascade = CascadeType.REMOVE)
    private Income income;

    @OneToMany(mappedBy = "logbook", cascade = CascadeType.REMOVE)
    private List<Expense> expense;

    public Logbook(String name) {
        this.name = name;

    }
}
```

## CategoryRequest.java

`src/main/java/com/panda/expense_tracker_2/dto/CategoryRequest.java`

```java
package com.panda.expense_tracker_2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class CategoryRequest {

    @NotBlank
    String name;
}
```

## CategoryResponse.java

`src/main/java/com/panda/expense_tracker_2/dto/CategoryResponse.java`

```java
package com.panda.expense_tracker_2.dto;

import lombok.Value;

@Value
public class CategoryResponse {
    Long id;
    String name;


}
```

## ErrorResponse.java

`src/main/java/com/panda/expense_tracker_2/dto/ErrorResponse.java`

```java
package com.panda.expense_tracker_2.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timestamp;
}
```

## ExpenseRequest.java

`src/main/java/com/panda/expense_tracker_2/dto/ExpenseRequest.java`

```java
package com.panda.expense_tracker_2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class ExpenseRequest {
    @NotBlank
    String name;

    @Positive
    @NotNull
    BigDecimal cost;

    LocalDate date;
    Long categoryId;
}
```

## ExpenseResponse.java

`src/main/java/com/panda/expense_tracker_2/dto/ExpenseResponse.java`

```java
package com.panda.expense_tracker_2.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class ExpenseResponse {
    Long id;
    String name;
    BigDecimal cost;
    LocalDate date;
    Long categoryId;


}
```

## IncomeRequest.java

`src/main/java/com/panda/expense_tracker_2/dto/IncomeRequest.java`

```java
package com.panda.expense_tracker_2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class IncomeRequest {

    @NotNull
    @Positive
    BigDecimal amount;

    LocalDate date;

}
```

## LogbookRequest.java

`src/main/java/com/panda/expense_tracker_2/dto/LogbookRequest.java`

```java
package com.panda.expense_tracker_2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class LogbookRequest {

    @NotBlank
    String name;

    @NotNull
    @Positive
    BigDecimal incomeAmount;
    LocalDate incomeDate;
}
```

## LogbookResponse.java

`src/main/java/com/panda/expense_tracker_2/dto/LogbookResponse.java`

```java
package com.panda.expense_tracker_2.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class LogbookResponse {
    Long id;
    String name;
    BigDecimal incomeAmount;
    LocalDate incomeDate;



}
```

## SummaryResponse.java

`src/main/java/com/panda/expense_tracker_2/dto/SummaryResponse.java`

```java
package com.panda.expense_tracker_2.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class SummaryResponse {
    BigDecimal totalExpenditure;
    BigDecimal income;
    BigDecimal difference;


}
```

## CategoryRepository.java

`src/main/java/com/panda/expense_tracker_2/repository/CategoryRepository.java`

```java
package com.panda.expense_tracker_2.repository;

import com.panda.expense_tracker_2.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
    public List<Category> findByActiveTrue();

    public Optional<Category> findByNameIgnoreCase(String name);
}
```

## ExpenseRepository.java

`src/main/java/com/panda/expense_tracker_2/repository/ExpenseRepository.java`

```java
package com.panda.expense_tracker_2.repository;

import com.panda.expense_tracker_2.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    public List<Expense> findByLogbookId(Long logbookId);

    @Query("SELECT COALESCE(SUM(e.cost), 0) FROM Expense e WHERE e.logbook.id = :logbookId")
    public BigDecimal sumCostByLogbookId(@Param("logbookId") Long logbookId);
}
```

## IncomeRepository.java

`src/main/java/com/panda/expense_tracker_2/repository/IncomeRepository.java`

```java
package com.panda.expense_tracker_2.repository;

import com.panda.expense_tracker_2.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    public Optional<Income> findByLogbookId(Long id);
}
```

## LogbookRepository.java

`src/main/java/com/panda/expense_tracker_2/repository/LogbookRepository.java`

```java
package com.panda.expense_tracker_2.repository;

import com.panda.expense_tracker_2.model.Logbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogbookRepository extends JpaRepository<Logbook, Long> {

}
```

## BadRequestException.java

`src/main/java/com/panda/expense_tracker_2/exception/BadRequestException.java`

```java
package com.panda.expense_tracker_2.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
```

## ConflictException.java

`src/main/java/com/panda/expense_tracker_2/exception/ConflictException.java`

```java
package com.panda.expense_tracker_2.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
```

## NotFoundException.java

`src/main/java/com/panda/expense_tracker_2/exception/NotFoundException.java`

```java
package com.panda.expense_tracker_2.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
```

## RestExceptionHandler.java

`src/main/java/com/panda/expense_tracker_2/exception/RestExceptionHandler.java`

```java
package com.panda.expense_tracker_2.exception;

import com.panda.expense_tracker_2.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
        ErrorResponse body = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        ErrorResponse body = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).toList().toString();
        ErrorResponse body = new ErrorResponse(message, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex){
        ErrorResponse body = new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value(), LocalDateTime.now());
        return  new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid value '" + ex.getValue() + "' for parameter '" + ex.getName() + "'";
        ErrorResponse body = new ErrorResponse(message, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {
        ErrorResponse body = new ErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

## CategoryService.java

`src/main/java/com/panda/expense_tracker_2/service/CategoryService.java`

```java
package com.panda.expense_tracker_2.service;

import com.panda.expense_tracker_2.dto.CategoryRequest;
import com.panda.expense_tracker_2.dto.CategoryResponse;
import com.panda.expense_tracker_2.exception.ConflictException;
import com.panda.expense_tracker_2.exception.NotFoundException;
import com.panda.expense_tracker_2.model.Category;
import com.panda.expense_tracker_2.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryService {
    final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryRequest request) {
        Optional<Category> existing = categoryRepository.findByNameIgnoreCase(request.getName());
        Category saved = null;
        if (existing.isEmpty()) {
            saved = categoryRepository.save(new Category(request.getName()));
        } else {
            Category found = existing.get();
            if (found.isActive()) {
                throw new ConflictException("Category already exists");
            } else {
                found.setActive(true);
                saved = categoryRepository.save(found);
            }
        }
        return new CategoryResponse(saved.getId(), saved.getName());
    }

    public List<CategoryResponse> listCategories(){
        List<Category> categories = categoryRepository.findByActiveTrue();
        return categories.stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    public CategoryResponse deleteCategory(Long id){
        Category found = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found."));
        found.setActive(false);
        Category saved =  categoryRepository.save(found);
        return new CategoryResponse(saved.getId(), saved.getName());
    }




}
```

## ExpenseService.java

`src/main/java/com/panda/expense_tracker_2/service/ExpenseService.java`

```java
package com.panda.expense_tracker_2.service;

import com.panda.expense_tracker_2.dto.ExpenseRequest;
import com.panda.expense_tracker_2.dto.ExpenseResponse;
import com.panda.expense_tracker_2.exception.BadRequestException;
import com.panda.expense_tracker_2.exception.NotFoundException;
import com.panda.expense_tracker_2.model.Category;
import com.panda.expense_tracker_2.model.Expense;
import com.panda.expense_tracker_2.model.Logbook;
import com.panda.expense_tracker_2.repository.CategoryRepository;
import com.panda.expense_tracker_2.repository.ExpenseRepository;
import com.panda.expense_tracker_2.repository.LogbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final LogbookRepository logbookRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseResponse logExpense(Long logbookId, ExpenseRequest request) {
        Category categoryFound = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new NotFoundException("category not found"));
        if (!categoryFound.isActive()) {
            throw new BadRequestException("category is not active");
        }
        Logbook logbookFound = logbookRepository.findById(logbookId).orElseThrow(() -> new NotFoundException("logbook not found"));
        Expense expense = expenseRepository.save(new Expense(request.getName(), request.getCost(), request.getDate(), categoryFound, logbookFound));

        return new ExpenseResponse(expense.getId(), expense.getName(), expense.getCost(), expense.getDate(), expense.getCategory().getId());
    }

    public List<ExpenseResponse> getExpenses(Long logbookId) {
        Logbook logbookFound = logbookRepository.findById(logbookId).orElseThrow(() -> new NotFoundException("Logbook not found"));
        return expenseRepository.findByLogbookId(logbookId).stream()
                .map(e -> new ExpenseResponse(e.getId(), e.getName(), e.getCost(), e.getDate(), e.getCategory().getId()))
                .toList();
    }

    public ExpenseResponse deleteExpense(Long expenseId) {
        Expense found = expenseRepository
                .findById(expenseId)
                .orElseThrow(() -> new NotFoundException("Expense not found"));
        ExpenseResponse response = new ExpenseResponse(found.getId(), found.getName(), found.getCost(), found.getDate(), found.getCategory().getId());
        expenseRepository.delete(found);
        return response;
    }

}
```

## LogbookService.java

`src/main/java/com/panda/expense_tracker_2/service/LogbookService.java`

```java
package com.panda.expense_tracker_2.service;

import com.panda.expense_tracker_2.dto.LogbookRequest;
import com.panda.expense_tracker_2.dto.LogbookResponse;
import com.panda.expense_tracker_2.exception.NotFoundException;
import com.panda.expense_tracker_2.model.Income;
import com.panda.expense_tracker_2.model.Logbook;
import com.panda.expense_tracker_2.repository.IncomeRepository;
import com.panda.expense_tracker_2.repository.LogbookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LogbookService {
    private final LogbookRepository logbookRepository;
    private final IncomeRepository incomeRepository;


    @Transactional
    public LogbookResponse createLogbook(LogbookRequest request){
        Logbook saved = logbookRepository.save(new Logbook(request.getName()));
        incomeRepository.save(new Income(request.getIncomeDate(), request.getIncomeAmount(), saved));
        return new LogbookResponse(saved.getId(), saved.getName(), request.getIncomeAmount(), request.getIncomeDate());
    }


    public LogbookResponse deleteLogbook(Long id){
    Logbook found = logbookRepository.findById(id).orElseThrow(() -> new NotFoundException("Logbook not found"));
        Income income = incomeRepository.findByLogbookId(id).orElse(null);
        logbookRepository.delete(found);
        return new LogbookResponse(found.getId(), found.getName(),
                income == null ? null : income.getAmount(),
                income == null ? null : income.getDate());
    }

    public List<LogbookResponse> listLogbooks() {
        return logbookRepository.findAll().stream().map(l -> {
            Income i = incomeRepository.findByLogbookId(l.getId()).orElse(null);
            return new LogbookResponse(l.getId(), l.getName(),
                    i == null ? null : i.getAmount(),
                    i == null ? null : i.getDate());
        }).toList();
    }

}
```

## SummaryService.java

`src/main/java/com/panda/expense_tracker_2/service/SummaryService.java`

```java
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
```

## CategoryController.java

`src/main/java/com/panda/expense_tracker_2/controller/CategoryController.java`

```java
package com.panda.expense_tracker_2.controller;

import com.panda.expense_tracker_2.dto.CategoryRequest;
import com.panda.expense_tracker_2.dto.CategoryResponse;
import com.panda.expense_tracker_2.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody
            CategoryRequest categoryRequest

    ){
        return new ResponseEntity<>(categoryService.createCategory(categoryRequest),
                HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getCategory(){
        return  new ResponseEntity<>(categoryService.listCategories(), HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")

    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable Long id){
        return  new ResponseEntity<>(categoryService.deleteCategory(id), HttpStatus.OK);
    }
}

```

## ExpenseController.java

`src/main/java/com/panda/expense_tracker_2/controller/ExpenseController.java`

```java
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
```

## LogbookController.java

`src/main/java/com/panda/expense_tracker_2/controller/LogbookController.java`

```java
package com.panda.expense_tracker_2.controller;

import com.panda.expense_tracker_2.dto.CategoryRequest;
import com.panda.expense_tracker_2.dto.CategoryResponse;
import com.panda.expense_tracker_2.dto.LogbookRequest;
import com.panda.expense_tracker_2.dto.LogbookResponse;
import com.panda.expense_tracker_2.service.LogbookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LogbookController {
    private final LogbookService logbookService;

    @PostMapping("/logbooks")
    public ResponseEntity<LogbookResponse> createLogbook(
            @Valid
            @RequestBody
            LogbookRequest logbookRequest
    ){
        return new ResponseEntity<LogbookResponse>(logbookService.createLogbook(logbookRequest),
                HttpStatus.CREATED);
    }

    @GetMapping("/logbooks")
    public ResponseEntity<List<LogbookResponse>> getLogbook(){
        return new ResponseEntity<>(logbookService.listLogbooks(),
                HttpStatus.OK);
    }

    @DeleteMapping("/logbooks/{id}")
    public ResponseEntity<LogbookResponse> deleteLogbook(@PathVariable Long id){
        return new ResponseEntity<LogbookResponse>(logbookService.deleteLogbook(id),
                HttpStatus.OK);
    }
}
```

## SummaryController.java

`src/main/java/com/panda/expense_tracker_2/controller/SummaryController.java`

```java
package com.panda.expense_tracker_2.controller;

import com.panda.expense_tracker_2.dto.SummaryResponse;
import com.panda.expense_tracker_2.service.SummaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SummaryController {
    private final SummaryService summaryService;

    @GetMapping("/logbooks/{logbookId}/summary")
    public ResponseEntity<SummaryResponse> getSummary(
            @PathVariable
            Long logbookId
    ){
        return new ResponseEntity<SummaryResponse>(summaryService.summary(logbookId),
                HttpStatus.OK);
    }
}
```

## User.java

`src/main/java/com/panda/expense_tracker_2/model/User.java`

```java
package com.panda.expense_tracker_2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Locale;

@Setter
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String passwordHash;

    @Column(nullable = false)
    LocalDateTime createdAt;

    @PrePersist
    @PreUpdate
    private void normalizeEmail(){
        if(email != null) {
            email = email.trim().toLowerCase(Locale.ROOT);
        }
    }

    public User(String email, LocalDateTime createdAt, String passwordHash) {
        this.email = email;
        this.createdAt = createdAt;
        this.passwordHash = passwordHash;
    }
}
```

## UserRepository.java

`src/main/java/com/panda/expense_tracker_2/repository/UserRepository.java`

```java
package com.panda.expense_tracker_2.repository;

import com.panda.expense_tracker_2.model.Income;
import com.panda.expense_tracker_2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
}
```

## UserRequest.java

`src/main/java/com/panda/expense_tracker_2/dto/UserRequest.java`

```java
package com.panda.expense_tracker_2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UserRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;
}
```

## UserResponse.java

`src/main/java/com/panda/expense_tracker_2/dto/UserResponse.java`

```java
package com.panda.expense_tracker_2.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UserResponse {
    @Id
    private Long id;

    @NotBlank
    private String email;

    private LocalDateTime createdAt;
}
```

## ApplicationConfig.java

`src/main/java/com/panda/expense_tracker_2/config/ApplicationConfig.java`

```java
package com.panda.expense_tracker_2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();
    }
}
```

## SecurityConfig.java

`src/main/java/com/panda/expense_tracker_2/config/SecurityConfig.java`

```java
package com.panda.expense_tracker_2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
```

## AuthService.java

`src/main/java/com/panda/expense_tracker_2/service/AuthService.java`

```java
package com.panda.expense_tracker_2.service;

import com.panda.expense_tracker_2.dto.UserRequest;
import com.panda.expense_tracker_2.dto.UserResponse;
import com.panda.expense_tracker_2.exception.ConflictException;
import com.panda.expense_tracker_2.model.User;
import com.panda.expense_tracker_2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse register(UserRequest request){
        String email = request.getEmail().trim().toLowerCase(Locale.ROOT);
        Optional<User> exists = userRepository.findByEmail(email);
        if (exists.isPresent()){
            throw new ConflictException("email already exists");
        }
        String hash = passwordEncoder.encode(request.getPassword());

        User user = userRepository.save(new User(email, LocalDateTime.now(), hash));

        return new UserResponse(user.getId(), user.getEmail(), user.getCreatedAt());
    }
}
```

## AuthController.java

`src/main/java/com/panda/expense_tracker_2/controller/AuthController.java`

```java
package com.panda.expense_tracker_2.controller;

import com.panda.expense_tracker_2.dto.UserRequest;
import com.panda.expense_tracker_2.dto.UserResponse;
import com.panda.expense_tracker_2.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(
            @RequestBody
            @Valid
            UserRequest userRequest
    ){
        return new ResponseEntity<>(authService.register(userRequest), HttpStatus.CREATED);
    }
}
```

## ExpenseTrackerT2Application.java

`src/main/java/com/panda/expense_tracker_2/ExpenseTrackerT2Application.java`

```java
package com.panda.expense_tracker_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpenseTrackerT2Application {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerT2Application.class, args);
    }

}
```

## ExpenseTrackerT2ApplicationTests.java

`src/test/java/com/panda/expense_tracker_2/ExpenseTrackerT2ApplicationTests.java`

```java
package com.panda.expense_tracker_2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExpenseTrackerT2ApplicationTests {

    @Test
    void contextLoads() {
    }

}
```

## AuthControllerTest.java

`src/test/java/com/panda/expense_tracker_2/controller/AuthControllerTest.java`

```java
package com.panda.expense_tracker_2.controller;

import com.panda.expense_tracker_2.dto.UserRequest;
import com.panda.expense_tracker_2.dto.UserResponse;
import com.panda.expense_tracker_2.exception.ConflictException;
import com.panda.expense_tracker_2.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Test
    void InvalidEmail() throws Exception{
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "blaj-jf",
                            "password": "myuhsd@347"
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("[email: must be a well-formed email address]"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.timestamp").exists());

    }

    @Test
    void WeakPasswordThrows400() throws Exception{
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "nlagiadk23@gmail.com",
                            "password": "gs1"
                            }
                        """))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("[password: size must be between 8 and 2147483647]"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void SuccessThrows201() throws Exception{
        when(authService.register(any(UserRequest.class)))
                .thenReturn(new UserResponse(1L, "actuallu342@gmail.com", LocalDateTime.now()));
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "email": "actuallu342@gmail.com",
                                "password": "Kyfway#4567"
                            }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("actuallu342@gmail.com"))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void DuplicateEmailThrows409() throws Exception {
        when(authService.register(any(UserRequest.class)))
                .thenThrow(new ConflictException("email already exists"));
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "email": "actuallu342@gmail.com",
                                "password": "Kyfway#4567"
                            }
                        """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("email already exists"))
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
```

## UserRepositoryTest.java

`src/test/java/com/panda/expense_tracker_2/repository/UserRepositoryTest.java`

```java
package com.panda.expense_tracker_2.repository;

import com.panda.expense_tracker_2.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest(properties = { "spring.jpa.hibernate.ddl-auto=create-drop", "spring.flyway.enabled=false" })
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void emailWithMoreThanOneEntries() {
        User user = new User();
        user.setEmail("a@b.com");
        user.setCreatedAt(LocalDateTime.now());
        user.setPasswordHash("hash1");
        User saved = userRepository.saveAndFlush(user);

        User user2 = new User();
        user2.setEmail("a@b.com");
        user2.setCreatedAt(LocalDateTime.now());
        user2.setPasswordHash("blah");
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.saveAndFlush(user2));

    }
}
```

## SummaryServiceTest.java

`src/test/java/com/panda/expense_tracker_2/service/SummaryServiceTest.java`

```java
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
```

## ExpenseControllerTest.java

`src/test/java/com/panda/expense_tracker_2/controller/ExpenseControllerTest.java`

```java
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
                .andExpect(jsonPath("$.status").value(400))
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
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.timestamp").exists());

    }
}
```

## SummaryControllerTest.java

`src/test/java/com/panda/expense_tracker_2/controller/SummaryControllerTest.java`

```java
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
```
