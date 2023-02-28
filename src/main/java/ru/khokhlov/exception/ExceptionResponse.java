package ru.khokhlov.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ExceptionResponse {
    String massage;

    public ExceptionResponse(String message) {
        this.massage = message;
    }
}
