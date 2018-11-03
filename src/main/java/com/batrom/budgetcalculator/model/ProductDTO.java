package com.batrom.budgetcalculator.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
public class ProductDTO {
    private Long id;
    private String description;
    private BigDecimal price;
    private String creditor;
    private String debtorsGroup;
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

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", creditor='" + creditor + '\'' +
                ", debtorsGroup='" + debtorsGroup + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}