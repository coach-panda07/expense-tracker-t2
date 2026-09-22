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


