package com.app2.tasklytodo.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public ResourceNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }
}
