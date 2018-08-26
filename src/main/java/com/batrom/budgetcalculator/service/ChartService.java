package com.batrom.budgetcalculator.service;

import com.batrom.budgetcalculator.dto.DoughnutChartDTO;
import com.batrom.budgetcalculator.model.Product;
import com.batrom.budgetcalculator.util.DateUtils;
import com.batrom.budgetcalculator.wrapper.ChartDataWrapper;
import com.batrom.budgetcalculator.wrapper.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.*;

@Service
public class ChartService {

    public List<DoughnutChartDTO> getData(final String memberName) {
        return productService.findProductsByDebtorOrCreditorName(memberName)
                                     .stream()
                                     .map(this::productToChartDataWrapper)
                                     .collect(groupingBy(ChartDataWrapper::getDate,
                                             groupingBy(ChartDataWrapper::getLabel,
                                                     reducing(BigDecimal.ZERO, ChartDataWrapper::getValue, BigDecimal::add))))
                                     .entrySet()
                                     .stream()
                                     .map(entry -> new DoughnutChartDTO(entry.getKey(), entry.getValue()
                                                                                             .entrySet()
                                                                                             .stream()
                                                                                             .map(innerEntry -> new KeyValue<>(innerEntry.getKey(), innerEntry.getValue().setScale(2, BigDecimal.ROUND_HALF_UP)))
                                                                                             .collect(toList())))
                                     .collect(toList());
    }

    private ChartDataWrapper productToChartDataWrapper(final Product product) {
        return new ChartDataWrapper(getFormattedDate(product.getCreationDate()), product.getCategory().getPolishName(),
                product.getPrice().divide(BigDecimal.valueOf(product.getDebtorGroup().getMembers().size()), 8, RoundingMode.HALF_UP));
    }

    private String getFormattedDate(final LocalDate date) {
        return DateUtils.mapToString(date, "MM.yyyy");
    }

    private ProductService productService;

    @Autowired
    public ChartService(final ProductService productService) {
        this.productService = productService;
    }
}
