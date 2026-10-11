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