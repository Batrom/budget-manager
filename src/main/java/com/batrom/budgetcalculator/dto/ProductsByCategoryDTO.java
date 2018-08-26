package com.batrom.budgetcalculator.dto;

import com.batrom.budgetcalculator.enums.Category;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@ToString
public class ProductsByCategoryDTO {
    private String category;
    private List<ProductDTO> products;

    public ProductsByCategoryDTO(final Map.Entry<Category, List<ProductDTO>> entry) {
        this.category = entry.getKey().name();
        this.products = entry.getValue();
    }

    public ProductsByCategoryDTO(final Category categoryEnum) {
        this.category = categoryEnum.name();
        this.products = new ArrayList<>();
    }
}
