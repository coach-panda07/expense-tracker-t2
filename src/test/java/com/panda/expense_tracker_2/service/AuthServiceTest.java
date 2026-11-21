package com.panda.expense_tracker_2.service;

import com.panda.expense_tracker_2.dto.UserRequest;
import com.panda.expense_tracker_2.exception.ConflictException;
import com.panda.expense_tracker_2.model.User;
import com.panda.expense_tracker_2.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthService authService;

    @Test
    void storedHashDiffersFromRawPassword() {
        String rawPassword = "rawPassword1";
        String hash = "$2a$10$hashedValue";
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(rawPassword)).thenReturn(hash);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        authService.register(new UserRequest("test@example.com", rawPassword));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertNotEquals(rawPassword, captor.getValue().getPasswordHash());
        assertEquals(hash, captor.getValue().getPasswordHash());
    }

    @Test
    void emailIsNormalized() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        authService.register(new UserRequest("  Test@Example.COM  ", "rawPassword1"));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertEquals("test@example.com", captor.getValue().getEmail());
    }

    @Test
    void duplicateEmailThrowsConflictException() {
        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(new User("test@example.com", LocalDateTime.now(), "hash")));

        ConflictException ex = assertThrows(ConflictException.class,
                () -> authService.register(new UserRequest("test@example.com", "rawPassword1")));

        assertEquals("email already exists", ex.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void concurrentDuplicateThrowsConflictException() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class)))
                .thenThrow(new DataIntegrityViolationException("duplicate email"));

        ConflictException ex = assertThrows(ConflictException.class,
                () -> authService.register(new UserRequest("test@example.com", "rawPassword1")));

        assertEquals("email already exists", ex.getMessage());
    }
}
