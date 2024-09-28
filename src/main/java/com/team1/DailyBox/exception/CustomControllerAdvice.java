package com.team1.DailyBox.exception;

import com.team1.DailyBox.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomControllerAdvice {
    private static final ApiResponse<String> response = new ApiResponse<>();

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<String>> customException(CustomException e) {
        response.setMessage(e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getCode()).body(response);
    }
}
