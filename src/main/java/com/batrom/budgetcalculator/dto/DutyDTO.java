package com.batrom.budgetcalculator.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Objects;

@Data
@ToString
public class DutyDTO {
    private Long id;
    private String description;
    private String orderer;
    private String executor;
    private String date;
    private Long points;
    private Boolean done;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DutyDTO)) return false;
        DutyDTO dutyDTO = (DutyDTO) o;
        return Objects.equals(id, dutyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
