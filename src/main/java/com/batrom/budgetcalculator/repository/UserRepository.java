package com.batrom.budgetcalculator.repository;

import com.batrom.budgetcalculator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(final String name);

}
