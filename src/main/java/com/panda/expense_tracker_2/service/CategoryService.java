package com.panda.expense_tracker_2.service;

import com.panda.expense_tracker_2.dto.CategoryRequest;
import com.panda.expense_tracker_2.dto.CategoryResponse;
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
        Optional<Category> found = categoryRepository.findByActiveTrue(id);
        if (category.isActive() == true) {
            category.setActive == false;
        }


    }



}
