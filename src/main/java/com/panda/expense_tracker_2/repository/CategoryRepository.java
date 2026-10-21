package com.panda.expense_tracker_2.repository;

import com.panda.expense_tracker_2.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
    public List<Category> findByActiveTrue();

    public Optional<Category> findByNameIgnoreCase(String name);
}
