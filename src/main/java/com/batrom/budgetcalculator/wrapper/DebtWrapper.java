package com.batrom.budgetcalculator.wrapper;

import com.batrom.budgetcalculator.model.Member;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.UnaryOperator;

@Data
public class DebtWrapper {
    private Member creditor;
    private Member debtor;
    private BigDecimal amount;
    private LocalDate date;

    public static UnaryOperator<DebtWrapper> reverse() {
        return wrapper -> {
            final DebtWrapper reversedWrapper = new DebtWrapper();
            reversedWrapper.setCreditor(wrapper.getDebtor());
            reversedWrapper.setDebtor(wrapper.getCreditor());
            reversedWrapper.setAmount(wrapper.getAmount().negate());
            reversedWrapper.setDate(wrapper.getDate());
            return reversedWrapper;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DebtWrapper)) return false;

        DebtWrapper wrapper = (DebtWrapper) o;
        return Objects.equals(creditor, wrapper.creditor) && Objects.equals(debtor, wrapper.debtor) && Objects.equals(date, wrapper.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditor, debtor, date);
    }
}
