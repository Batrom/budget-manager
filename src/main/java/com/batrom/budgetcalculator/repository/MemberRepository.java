package com.batrom.budgetcalculator.repository;

import com.batrom.budgetcalculator.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByName(final String name);
}
