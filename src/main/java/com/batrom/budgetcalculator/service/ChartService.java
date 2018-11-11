package com.batrom.budgetcalculator.service;

import com.batrom.budgetcalculator.dto.DoughnutChartDTO;
import com.batrom.budgetcalculator.model.Member;
import com.batrom.budgetcalculator.model.Product;
import com.batrom.budgetcalculator.util.DateUtils;
import com.batrom.budgetcalculator.wrapper.ChartDataWrapper;
import com.batrom.budgetcalculator.wrapper.KeyValue;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.*;

@Service
public class ChartService {

    public List<DoughnutChartDTO> getData(final String memberGroup) {
        final Set<Member> debtors = memberGroupService.findByName(memberGroup).getMembers();
        return productService.findProductsByDebtors(debtors)
                             .stream()
                             .map(product -> ProductChartWrapper.create(product, debtors))
                             .map(this::productToChartDataWrapper)
                             .collect(groupingBy(ChartDataWrapper::getDate, groupingBy(ChartDataWrapper::getLabel, reducing(BigDecimal.ZERO, ChartDataWrapper::getValue, BigDecimal::add))))
                             .entrySet()
                             .stream()
                             .map(entry -> new DoughnutChartDTO(entry.getKey(), entry.getValue()
                                                                                     .entrySet()
                                                                                     .stream()
                                                                                     .map(innerEntry -> new KeyValue<>(innerEntry.getKey(), innerEntry
                                                                                             .getValue()
                                                                                             .setScale(2, BigDecimal.ROUND_HALF_UP)))
                                                                                     .collect(toList())))
                             .collect(toList());
    }

    private ChartDataWrapper productToChartDataWrapper(final ProductChartWrapper productChartWrapper) {
        return new ChartDataWrapper(getFormattedDate(productChartWrapper.product.getCreationDate()), productChartWrapper.product.getCategory().getPolishName(),
                productChartWrapper.product.getPrice().divide(BigDecimal.valueOf(productChartWrapper.numberToDivideBy), 8, RoundingMode.HALF_UP));
    }

    private String getFormattedDate(final LocalDate date) {
        return DateUtils.mapToString(date, "MM.yyyy");
    }

    private final ProductService productService;
    private final MemberGroupService memberGroupService;

    public ChartService(final ProductService productService, final MemberGroupService memberGroupService) {
        this.productService = productService;
        this.memberGroupService = memberGroupService;
    }

    private static class ProductChartWrapper {
        private final Product product;
        private final double numberToDivideBy;

        private ProductChartWrapper(final Product product, final double numberToDivideBy) {
            this.product = product;
            this.numberToDivideBy = numberToDivideBy;
        }

        private static ProductChartWrapper create(final Product product, final Set<Member> debtors) {
            final double productDebtorListSize = product.getDebtorGroup().getMembers().size();
            final double commonDebtors = product.getDebtorGroup()
                                             .getMembers()
                                             .stream()
                                             .filter(debtorFromProduct -> debtors.stream().anyMatch(givenDebtor -> givenDebtor.equals(debtorFromProduct)))
                                             .collect(toSet())
                                             .size();
            return new ProductChartWrapper(product, productDebtorListSize / commonDebtors);
        }
    }
}
