package com.batrom.budgetcalculator.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

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
        return StringUtils.isNotEmpty(polishName) ? Category.valueOf(polishName) : Category.NONE;
    }

    @Getter
    private String polishName;
}
