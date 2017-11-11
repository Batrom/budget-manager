package com.batrom.budgetcalculator.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@Table(name = "debt")
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User creditor;

    @ManyToOne(fetch = FetchType.LAZY)
    private User debtor;

    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private LocalDate creationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Debt)) return false;

        Debt debt = (Debt) o;
        return Objects.equals(id, debt.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}