package com.batrom.budgetcalculator.task;

import com.batrom.budgetcalculator.service.DebtService;
import com.batrom.budgetcalculator.service.ProductService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.batrom.budgetcalculator.util.DateUtils.getFirstDayOfLastMonth;
import static com.batrom.budgetcalculator.util.DateUtils.getFirstDayOfThisMonth;
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

    private final DebtService debtService;

    private final ProductService productService;

    @Autowired
    public DebtTask(final DebtService debtService, final ProductService productService) {
        this.debtService = debtService;
        this.productService = productService;
    }
}
