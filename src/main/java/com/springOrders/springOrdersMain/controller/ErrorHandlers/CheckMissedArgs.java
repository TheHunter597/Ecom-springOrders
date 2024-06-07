package com.springOrders.springOrdersMain.controller.ErrorHandlers;

import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class CheckMissedArgs {
    public ArrayList<String> validate(ArrayList<String> requiredArgs)
            throws IllegalArgumentException, IllegalAccessException {
        ArrayList<String> missedArgs = new ArrayList<>();

        for (Field arg : this.getClass().getDeclaredFields()) {
            if (requiredArgs.contains(arg.getName())) {
                arg.setAccessible(true);
                if (arg.get(this) == null) {
                    missedArgs.add(arg.getName());
                }
            }
        }
        if (missedArgs.size() > 0) {
            return missedArgs;
        }

        return null;
    }
}