package com.batrom.budgetcalculator.repository;

import com.batrom.budgetcalculator.model.Duty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DutyRepository extends JpaRepository<Duty, Long>{
}
