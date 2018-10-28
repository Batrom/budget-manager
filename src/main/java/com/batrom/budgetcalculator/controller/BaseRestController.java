package com.batrom.budgetcalculator.controller;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@CommonsLog
public abstract class BaseRestController {

    protected <T> ResponseEntity<T> get(final Supplier<T> supplier) {
        try {
            return new ResponseEntity<>(supplier.get(), HttpStatus.OK);
        } catch (final Exception e) {
            log.error("Problem occurred in REST request", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    protected <T, R> ResponseEntity<R> execute(final T input, final Function<T, R> function) {
        try {
            return new ResponseEntity<>(function.apply(input), HttpStatus.OK);
        } catch (final Exception e) {
            log.error("Problem occurred in REST request", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    protected <T> ResponseEntity<T> execute(final T input, final UnaryOperator<T> operator) {
        try {
            return new ResponseEntity<>(operator.apply(input), HttpStatus.OK);
        } catch (final Exception e) {
            log.error("Problem occurred in REST request", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
