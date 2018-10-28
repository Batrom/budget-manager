package com.batrom.budgetcalculator.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String mapToString(final LocalDate date, final String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate mapToLocalDate(final String date, final String pattern) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate getSixtyDaysAgo() {
        return LocalDate.now().minusDays(60);
    }

    public static LocalDate getFirstDayOfThisMonth() {
        return getFirstDayOfMonth(LocalDate.now());
    }

    public static LocalDate getFirstDayOfLastMonth() {
        return getFirstDayOfMonth(LocalDate.now().minusMonths(1));
    }

    public static LocalDate getFirstDayOfMonth(final LocalDate date) {
        return date.withDayOfMonth(1);
    }
}
