package com.team1.DailyBox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private T data;
    private String message;

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(String message) {
        this.message = message;
    }
}
