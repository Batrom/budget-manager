package com.batrom.budgetcalculator.task;

import com.batrom.budgetcalculator.service.DebtService;
import com.batrom.budgetcalculator.service.ProductService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.batrom.budgetcalculator.util.DateUtils.*;
import static com.batrom.budgetcalculator.util.FunctionUtils.wrapToBiFunction;

@CommonsLog
@Service
public class DebtTask {

    @Scheduled(cron = "0 0 1 1 * ?")
    @Transactional
    public void run() {
        debtService.save(wrapToBiFunction(productService::findProductsBetweenDates).andThen(debtService::calculateDebts)
                                                                                   .apply(getFirstDayOfLastMonth(), getFirstDayOfThisMonth()));
    }

    public void fillOldData() {
        final LocalDate startingDate = getFirstDayOfMonth(LocalDate.now().minusMonths(24));
        saveThenReturnStartingDate(startingDate);
    }

    private LocalDate saveThenReturnStartingDate(final LocalDate from) {
        final LocalDate to = getFirstDayOfMonth(from.plusMonths(1));
        debtService.save(wrapToBiFunction(productService::findProductsBetweenDates).andThen(debtService::calculateDebts)
                                                                                   .apply(from, to));
        if (!to.isAfter(LocalDate.now())) return saveThenReturnStartingDate(to);
        else return LocalDate.now().plusMonths(1);
    }

    private final DebtService debtService;

    private final ProductService productService;

    @Autowired
    public DebtTask(final DebtService debtService, final ProductService productService) {
        this.debtService = debtService;
        this.productService = productService;
    }
}
