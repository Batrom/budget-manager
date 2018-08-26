package com.batrom.budgetcalculator.repository;

import com.batrom.budgetcalculator.model.Debt;
import com.batrom.budgetcalculator.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebtRepository extends JpaRepository<Debt, Long> {

    List<Debt> findDebtsByDebtor(final Member debtor);
}
