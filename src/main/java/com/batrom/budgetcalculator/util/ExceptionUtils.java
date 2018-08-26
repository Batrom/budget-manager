package com.batrom.budgetcalculator.util;

import static java.util.Objects.isNull;

public class ExceptionUtils {

    public static <T, E extends RuntimeException> void throwExceptionIfNull(final T nullableObject, final E exc) {
        throwExceptionIf(isNull(nullableObject), exc);
    }

    public static <T, E extends RuntimeException> void throwExceptionIfNotNull(final T nullableObject, final E exc) {
        throwExceptionIfNot(isNull(nullableObject), exc);
    }

    public static <E extends RuntimeException> void throwExceptionIf(final Boolean condition, final E exc) {
        if (condition) throw exc;
    }

    public static <E extends RuntimeException> void throwExceptionIfNot(final Boolean condition, final E exc) {
        if (!condition) throw exc;
    }
}
