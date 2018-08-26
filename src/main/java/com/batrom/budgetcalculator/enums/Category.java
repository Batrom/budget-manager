package com.batrom.budgetcalculator.enums;

import lombok.Getter;

import java.util.Arrays;

public enum Category {
    NONE("Brak"),
    FOOD("Jedzenie"),
    TRANSPORT("Transport"),
    ENTERTAINMENT("Rozrywka"),
    CLOTHES("Odzież"),
    HEALTH("Zdrowie i higiena"),
    HOME("Mieszkanie"),
    OTHER("Inne");

    Category(final String polishName) {
        this.polishName = polishName;
    }

    public static Category getByPolishName(final String polishName) {
        return Arrays.stream(Category.values())
                     .filter(c -> c.getPolishName().equals(polishName))
                     .findAny()
                     .orElse(null);
    }

    @Getter
    private String polishName;
}
