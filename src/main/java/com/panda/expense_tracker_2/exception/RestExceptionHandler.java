package com.panda.expense_tracker_2.exception;

import com.panda.expense_tracker_2.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<com.panda.expense_tracker_2.dto.ErrorResponse> handleNotFound(NotFoundException ex) {
        com.panda.expense_tracker_2.dto.ErrorResponse body =
                new com.panda.expense_tracker_2.dto.ErrorResponse(ex.getMessage(),
                        HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<com.panda.expense_tracker_2.dto.ErrorResponse> handleBadRequest(BadRequestException ex) {
        com.panda.expense_tracker_2.dto.ErrorResponse body =
                new com.panda.expense_tracker_2.dto.ErrorResponse(ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<com.panda.expense_tracker_2.dto.ErrorResponse> handleConflict(ConflictException ex) {
        com.panda.expense_tracker_2.dto.ErrorResponse body =
                new com.panda.expense_tracker_2.dto.ErrorResponse(ex.getMessage(),
                        HttpStatus.CONFLICT.value(), LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorResponse body = new ErrorResponse(ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<com.panda.expense_tracker_2.dto.ErrorResponse> handleUnexpected(Exception ex) {
        log.error("Unhandled exception", ex);
        com.panda.expense_tracker_2.dto.ErrorResponse body =
                new com.panda.expense_tracker_2.dto.ErrorResponse("An unexpected error occurred",
                        HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatusCode statusCode, WebRequest request) {
        com.panda.expense_tracker_2.dto.ErrorResponse error =
                new com.panda.expense_tracker_2.dto.ErrorResponse(messageFor(ex), statusCode.value(),
                        LocalDateTime.now());
        return super.handleExceptionInternal(ex, error, headers, statusCode, request);
    }

    private String messageFor(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException validation) {
            return validation.getBindingResult().getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .toList()
                    .toString();
        }
        if (ex instanceof MethodArgumentTypeMismatchException mismatch) {
            return "Invalid value '" + mismatch.getValue() + "' for parameter '" + mismatch.getName() + "'";
        }
        if (ex instanceof HttpMessageNotReadableException) {
            return "Malformed JSON";
        }
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return "Method not allowed";
        }
        if (ex instanceof NoHandlerFoundException || ex instanceof NoResourceFoundException) {
            return "No endpoint found";
        }
        if (ex instanceof HttpMediaTypeNotSupportedException) {
            return "Unsupported media type";
        }
        if (ex instanceof MissingServletRequestParameterException missing) {
            return "Missing required parameter '" + missing.getParameterName() + "'";
        }
        return ex.getMessage();
    }
}
