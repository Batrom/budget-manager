package com.batrom.budgetcalculator.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CommonDataDTO {
    private List<String> memberGroups;
    private List<CategoryDTO> productCategories;
}
