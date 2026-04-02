package com.app2.tasklytodo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    public static <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        return new ResponseEntity<>(
                ApiResponse.<T>builder()
                        .success(true)
                        .message(message)
                        .data(data)
                        .build(),
                HttpStatus.OK
        );
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(String message, T data) {
        return new ResponseEntity<>(
                ApiResponse.<T>builder()
                        .success(true)
                        .message(message)
                        .data(data)
                        .build(),
                HttpStatus.CREATED
        );
    }

    public static ResponseEntity<ApiResponse<Void>> deleted(String message) {
        return new ResponseEntity<>(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message(message)
                        .build(),
                HttpStatus.OK
        );
    }
}
