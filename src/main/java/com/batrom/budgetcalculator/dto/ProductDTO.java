package com.batrom.budgetcalculator.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@ToString
public class ProductDTO {
    private Long id;
    private String description;
    private BigDecimal price;
    private String creditor;
    private String debtorGroup;
    private String category;
    private String creationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDTO)) return false;
        ProductDTO productDTO = (ProductDTO) o;
        return Objects.equals(id, productDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
