# Expense Tracker T2 — Current Code

Snapshot synced to latest Income, IncomeRepository, IncomeRequest, LogbookController, and LogbookService files.

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

    @Column(nullable = false)
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

@Entity
@Table(name = "Log_Book")
@NoArgsConstructor
public class Logbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

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

@Data
public class LogbookRequest {
    private Long id;
    private String name;
}
```

## LogbookResponse.java

`src/main/java/com/panda/expense_tracker_2/dto/LogbookResponse.java`

```java
package com.panda.expense_tracker_2.dto;

import lombok.Data;

@Data
public class LogbookResponse {
    private Long id;
    private String name;

    public LogbookResponse(Long id, String name){
        this.name = name;
        this.id = id;
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
import com.panda.expense_tracker_2.model.Logbook;
import com.panda.expense_tracker_2.repository.LogbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogbookService {
    private final LogbookRepository logbookRepository;

    public LogbookResponse createLogbook(LogbookRequest request){
        Logbook saved = logbookRepository.save(new Logbook(request.getName()));
        return new LogbookResponse(saved.getId(), saved.getName());
    }

    public LogbookResponse deleteLogbook(Long id){
        Logbook found = logbookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Logbook not found "));
        logbookRepository.delete(found);
        return new LogbookResponse(found.getId(), found.getName());
    }

    public List<LogbookResponse> listLogbook(){
        List<Logbook> logbooks = logbookRepository.findAll();
        return logbooks.stream()
                .map(logbook -> new LogbookResponse(logbook.getId(),logbook.getName()))
                .collect(Collectors.toList());
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

    @PostMapping("/logbook")
    public ResponseEntity<LogbookResponse> createLogbook(
            @Valid
            @RequestBody
            LogbookRequest logbookRequest
    ){
        return new ResponseEntity<LogbookResponse>(logbookService.createLogbook(logbookRequest),
                HttpStatus.CREATED);
    }

    @GetMapping("/list_logbooks")
    public ResponseEntity<List<LogbookResponse>> getLogbook(){
        return new ResponseEntity<>(logbookService.listLogbook(),
                HttpStatus.OK);
    }

    @DeleteMapping("/logbook/{id}")
    public ResponseEntity<LogbookResponse> deleteLogbook(@PathVariable Long id){
        return new ResponseEntity<LogbookResponse>(logbookService.deleteLogbook(id),
                HttpStatus.OK);
    }
}
```
