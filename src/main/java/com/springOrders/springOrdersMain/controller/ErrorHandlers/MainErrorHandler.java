package com.springOrders.springOrdersMain.controller.ErrorHandlers;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class MainErrorHandler {
    @ExceptionHandler(value = MissedArgs.class)
    public ResponseEntity<HashMap<String, Object>> handler(MissedArgs exc) {
        var response = new HashMap<String, Object>();
        response.put("message", exc.getMessage());
        response.put("errors", exc.getErrors());
        return new ResponseEntity<>(response, exc.status);
    }
}
