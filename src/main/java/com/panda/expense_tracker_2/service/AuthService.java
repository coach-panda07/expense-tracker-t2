package com.panda.expense_tracker_2.service;

import com.panda.expense_tracker_2.dto.UserRequest;
import com.panda.expense_tracker_2.dto.UserResponse;
import com.panda.expense_tracker_2.exception.BadRequestException;
import com.panda.expense_tracker_2.exception.ConflictException;
import com.panda.expense_tracker_2.model.User;
import com.panda.expense_tracker_2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final int BCRYPT_MAX_PASSWORD_BYTES = 72;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse register(UserRequest request) {
        String email = request.getEmail().trim().toLowerCase(Locale.ROOT);
        Optional<User> exists = userRepository.findByEmail(email);
        if (exists.isPresent()) {
            throw new ConflictException("email already exists");
        }
        if (passwordTooLong(request.getPassword())) {
            throw new BadRequestException("password must be at most 72 bytes");
        }
        String hash = passwordEncoder.encode(request.getPassword());

        User user;
        try {
            user = userRepository.save(User.builder()
                    .email(email)
                    .passwordHash(hash)
                    .createdAt(LocalDateTime.now())
                    .build());
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("email already exists");
        }

        return new UserResponse(user.getId(), user.getEmail(), user.getCreatedAt());
    }

    private boolean passwordTooLong(String password) {
        return password != null
                && password.getBytes(StandardCharsets.UTF_8).length > BCRYPT_MAX_PASSWORD_BYTES;
    }
}
