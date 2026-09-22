package com.panda.expense_tracker_2.repository;

import com.panda.expense_tracker_2.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
