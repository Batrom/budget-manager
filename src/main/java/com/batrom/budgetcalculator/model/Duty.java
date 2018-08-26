package com.batrom.budgetcalculator.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@ToString
@Table(name = "duty")
public class Duty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    private Member orderer;

    @ManyToOne
    private Member executor;

    private LocalDate date;

    private Long points;

    private Boolean done;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Duty)) return false;

        Duty duty = (Duty) o;
        return Objects.equals(id, duty.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
