package com.springOrders.springOrdersMain.controller.ErrorHandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.springframework.http.HttpStatus;

public class MissedArgs extends RuntimeException {
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    private String message;
    private HashMap<String, ArrayList<String>> errors = new HashMap<>();

    public MissedArgs(ArrayList<String> missedArgs) {
        for (String missedArg : missedArgs) {
            errors.put(missedArg, new ArrayList<String>(Arrays.asList("This field is required.")));
        }
        this.message = "Missed arguments";
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public HashMap<String, ArrayList<String>> getErrors() {
        return this.errors;
    }
}
