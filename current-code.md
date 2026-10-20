# Expense Tracker T2 — Current Code

Snapshot synced to latest Income, Expense, Logbook, DTO, and controller files.

## Category.java

`src/main/java/com/panda/expense_tracker_2/model/Category.java`

```java
package com.panda.expense_tracker_2.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Category")
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Column
    private boolean active = true;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
```

## Income.java

`src/main/java/com/panda/expense_tracker_2/model/Income.java`

```java
package com.panda.expense_tracker_2.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

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


    public  LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getId() { return id;}

    public void setId(Long id) { this.id = id; }

    public Logbook getLogbook() { return logbook; }

    public void setLogbook(Logbook logbook) { this.logbook = logbook; }

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
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

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


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getCost() { return cost; }

    public void setCost(BigDecimal cost) { this.cost = cost; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public Category getCategory() { return category; }

    public void setCategory(Category category) { this.category = category; }

    public Logbook getLogbook() { return logbook; }

    public void setLogbook(Logbook logbook) { this.logbook = logbook; }

    public Expense(String name, BigDecimal cost, LocalDate date, Category category, Logbook logbook) {
        this.name = name;
        this.cost = cost;
        this.date = date;
        this.category = category;
        this.logbook = logbook;
    }

}
```

## IncomeRequest.java

`src/main/java/com/panda/expense_tracker_2/dto/IncomeRequest.java`

```java
package com.panda.expense_tracker_2.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class IncomeRequest {

    private Long id;

    private BigDecimal amount;

    private LocalDate date;

}
```

## ExpenseRequest.java

`src/main/java/com/panda/expense_tracker_2/dto/ExpenseRequest.java`

```java
package com.panda.expense_tracker_2.dto;

import com.panda.expense_tracker_2.model.Category;
import com.panda.expense_tracker_2.model.Logbook;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseRequest {
    private String name;
    private BigDecimal cost;
    private LocalDate date;
    private Long categoryId;
}
```

## ExpenseResponse.java

`src/main/java/com/panda/expense_tracker_2/dto/ExpenseResponse.java`

```java
package com.panda.expense_tracker_2.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseResponse {
    private Long id;
    private String name;
    private BigDecimal cost;
    private LocalDate date;
    private Long categoryId;

    public ExpenseResponse(Long id, String name, BigDecimal cost, LocalDate date, Long categoryId) {
        this.name = name;
        this.id = id;
        this.cost = cost;
        this.date = date;
        this.categoryId = categoryId;
    }
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

## CategoryRepository.java

`src/main/java/com/panda/expense_tracker_2/repository/CategoryRepository.java`

```java
package com.panda.expense_tracker_2.repository;

import com.panda.expense_tracker_2.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
    public List<Category> findByActiveTrue();
}
```

## CategoryRequest.java

`src/main/java/com/panda/expense_tracker_2/dto/CategoryRequest.java`

```java
package com.panda.expense_tracker_2.dto;

import lombok.Data;

@Data
public class CategoryRequest {
    private Long id;

    private String name;
}
```

## CategoryResponse.java

`src/main/java/com/panda/expense_tracker_2/dto/CategoryResponse.java`

```java
package com.panda.expense_tracker_2.dto;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;

    private String name;

    public CategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
```

## CategoryService.java

`src/main/java/com/panda/expense_tracker_2/service/CategoryService.java`

```java
package com.panda.expense_tracker_2.service;

import com.panda.expense_tracker_2.dto.CategoryRequest;
import com.panda.expense_tracker_2.dto.CategoryResponse;
import com.panda.expense_tracker_2.model.Category;
import com.panda.expense_tracker_2.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryRequest request){
        Category saved = categoryRepository.save(new Category(request.getName()));
        return  new CategoryResponse(saved.getId(),saved.getName());
    }

    public List<CategoryResponse> listCategories(){
        List<Category> categories = categoryRepository.findByActiveTrue();
        return categories.stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    public CategoryResponse deleteCategory(Long id){
        Category found = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found."));
        found.setActive(false);
        Category saved =  categoryRepository.save(found);
        return new CategoryResponse(saved.getId(), saved.getName());
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

## ExpenseService.java

`src/main/java/com/panda/expense_tracker_2/service/ExpenseService.java`

```java
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
        Category categoryFound = categoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new RuntimeException("category not found"));
        if (!categoryFound.isActive()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"category is not active");
        }
        Logbook logbookFound = logbookRepository.findById(logbookId).orElseThrow(()-> new RuntimeException("logbook not found"));
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

## Logbook.java

`src/main/java/com/panda/expense_tracker_2/model/Logbook.java`

```java
package com.panda.expense_tracker_2.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

## LogbookRequest.java

`src/main/java/com/panda/expense_tracker_2/dto/LogbookRequest.java`

```java
package com.panda.expense_tracker_2.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LogbookRequest {
    private Long id;
    private String name;
    private BigDecimal incomeAmount;
    private LocalDate incomeDate;
}
```

## LogbookResponse.java

`src/main/java/com/panda/expense_tracker_2/dto/LogbookResponse.java`

```java
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

## LogbookService.java

`src/main/java/com/panda/expense_tracker_2/service/LogbookService.java`

```java
package com.panda.expense_tracker_2.service;

import com.panda.expense_tracker_2.dto.LogbookRequest;
import com.panda.expense_tracker_2.dto.LogbookResponse;
import com.panda.expense_tracker_2.model.Income;
import com.panda.expense_tracker_2.model.Logbook;
import com.panda.expense_tracker_2.repository.IncomeRepository;
import com.panda.expense_tracker_2.repository.LogbookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        Logbook found = logbookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Logbook not found "));
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
