package com.batrom.budgetcalculator.repository;

import com.batrom.budgetcalculator.model.Debt;
import com.batrom.budgetcalculator.model.Product;
import com.batrom.budgetcalculator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebtRepository extends JpaRepository<Debt, Long> {
    void removeByProduct(final Product product);

}
