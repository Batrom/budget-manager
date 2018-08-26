package com.batrom.budgetcalculator.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@AllArgsConstructor
public class ChartDataWrapper {
    private String date;
    private String label;
    private BigDecimal value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChartDataWrapper)) return false;

        ChartDataWrapper that = (ChartDataWrapper) o;
        return Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getLabel(), that.getLabel()) &&
                Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDate(), getLabel(), getValue());
    }
}
