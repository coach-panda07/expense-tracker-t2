package com.panda.expense_tracker_2.repository;

import com.panda.expense_tracker_2.model.Logbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogbookRepositiory extends JpaRepository<Logbook, Long> {

}
