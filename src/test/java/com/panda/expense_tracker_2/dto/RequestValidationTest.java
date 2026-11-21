package com.panda.expense_tracker_2.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestValidationTest {

    @Test
    void incomeRequestRejectsNullDate() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<IncomeRequest>> violations =
                    validator.validate(new IncomeRequest(BigDecimal.TEN, null));

            assertEquals(1, violations.size());
            assertEquals("date", violations.iterator().next().getPropertyPath().toString());
        }
    }

    @Test
    void logbookRequestRejectsNullIncomeDate() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<LogbookRequest>> violations =
                    validator.validate(new LogbookRequest("Trip", BigDecimal.TEN, null));

            assertEquals(1, violations.size());
            assertEquals("incomeDate", violations.iterator().next().getPropertyPath().toString());
        }
    }
}
