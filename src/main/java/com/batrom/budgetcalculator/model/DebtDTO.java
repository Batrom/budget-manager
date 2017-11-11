package com.batrom.budgetcalculator.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
public class DebtDTO {
    private String me;
    private String otherUser;
    private BigDecimal debt;
    private String creationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DebtDTO)) return false;

        DebtDTO debtDTO = (DebtDTO) o;
        return Objects.equals(me, debtDTO.me) && Objects.equals(otherUser, debtDTO.otherUser) &&  Objects.equals(creationDate, debtDTO.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(me, otherUser, creationDate);
    }
}