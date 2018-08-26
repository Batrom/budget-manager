package com.batrom.budgetcalculator.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@ToString
public class DebtDTO {
    private String me;
    private String otherMember;
    private BigDecimal debt;
    private String creationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DebtDTO)) return false;

        DebtDTO debtDTO = (DebtDTO) o;
        return Objects.equals(me, debtDTO.me) && Objects.equals(otherMember, debtDTO.otherMember) && Objects.equals
                (creationDate, debtDTO.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(me, otherMember, creationDate);
    }
}