package com.panda.expense_tracker_2.repository;

import com.panda.expense_tracker_2.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest(properties = { "spring.jpa.hibernate.ddl-auto=create-drop", "spring.flyway.enabled=false" })
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void emailWithMoreThanOneEntries() {
        User user = new User();
        user.setEmail("a@b.com");
        user.setCreatedAt(LocalDateTime.now());
        user.setPasswordHash("hash1");
        User saved = userRepository.saveAndFlush(user);

        User user2 = new User();
        user2.setEmail("a@b.com");
        user2.setCreatedAt(LocalDateTime.now());
        user2.setPasswordHash("blah");
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.saveAndFlush(user2));

    }
}