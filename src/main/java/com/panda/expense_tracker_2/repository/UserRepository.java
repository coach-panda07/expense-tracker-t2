package com.panda.expense_tracker_2.repository;

import com.panda.expense_tracker_2.model.Income;
import com.panda.expense_tracker_2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
}
