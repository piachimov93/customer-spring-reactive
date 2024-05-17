package com.piachimov.springreactive.exception.handler;

import com.piachimov.springreactive.exception.CustomerApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerApiException.class)
    public ResponseEntity<?> handleBookAPIException(CustomerApiException customerApiException) {
        log.error("GlobalExceptionHandler::handleCustomerApiException: {}", customerApiException.getMessage(), customerApiException);

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error message", customerApiException.getMessage());
        errorMap.put("status", HttpStatus.BAD_REQUEST.toString());

        return ResponseEntity.ok(errorMap);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleGenericException(RuntimeException exception) {
        log.error("GlobalExceptionHandler::handleGenericException: {}", exception.getMessage(), exception);

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error message", exception.getMessage());
        errorMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());

        return ResponseEntity.ok(errorMap);
    }
}
