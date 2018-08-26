package com.batrom.budgetcalculator.util;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionUtils {
    public static <T, R> Function<T, R> wrapToFunction(final Function<T, R> function) {
        return function;
    }


    public static <T, S, R> BiFunction<T, S, R> wrapToBiFunction(final BiFunction<T, S, R> biFunction) {
        return biFunction;
    }

    public static <T> Consumer<T> wrapToConsumer(final Consumer<T> consumer) {
        return consumer;
    }

    public static <T> Predicate<T> wrapToPredicate(final Predicate<T> predicate) {
        return predicate;
    }
}
