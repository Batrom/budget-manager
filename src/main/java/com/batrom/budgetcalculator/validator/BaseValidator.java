package com.batrom.budgetcalculator.validator;

public interface BaseValidator<T> {
    void validate(final T value);
}
