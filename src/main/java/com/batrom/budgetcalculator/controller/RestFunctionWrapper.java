package com.batrom.budgetcalculator.controller;

import com.batrom.budgetcalculator.validator.BaseValidator;
import lombok.AllArgsConstructor;

import java.util.function.Function;

@AllArgsConstructor
public class RestFunctionWrapper<T, R> {
    private Function<T, R> function;
    private BaseValidator<T> validator;
    private String errorLogMessage;
}
