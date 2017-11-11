package com.batrom.budgetcalculator.repository;

import com.batrom.budgetcalculator.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long>{
    UserGroup findByName(final String name);
}
