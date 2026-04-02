package com.app2.tasklytodo.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    private final HttpStatus status;

    public BadRequestException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
