package com.batrom.budgetcalculator.repository;

import com.batrom.budgetcalculator.model.Product;
import com.batrom.budgetcalculator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByCreditor(final User user);
}
