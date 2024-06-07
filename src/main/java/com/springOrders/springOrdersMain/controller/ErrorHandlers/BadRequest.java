package com.springOrders.springOrdersMain.controller.ErrorHandlers;

import org.springframework.http.HttpStatus;

public class BadRequest extends RuntimeException {
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    private String message;

    public BadRequest(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
