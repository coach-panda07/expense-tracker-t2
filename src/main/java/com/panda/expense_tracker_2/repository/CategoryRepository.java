package com.panda.expense_tracker_2.repository;

import com.panda.expense_tracker_2.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
    public List<Category> findByActiveTrue(Long id);
}
