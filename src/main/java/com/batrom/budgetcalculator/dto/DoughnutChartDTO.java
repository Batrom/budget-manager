package com.batrom.budgetcalculator.dto;

import com.batrom.budgetcalculator.wrapper.KeyValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class DoughnutChartDTO {
    private String date;
    private List<KeyValue<String, BigDecimal>> labelValueList;
}
