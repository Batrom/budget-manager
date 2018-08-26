package com.batrom.budgetcalculator.repository;

import com.batrom.budgetcalculator.model.Member;
import com.batrom.budgetcalculator.model.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberGroupRepository extends JpaRepository<MemberGroup, Long>{
    MemberGroup findByName(final String name);

    @Query("SELECT DISTINCT gr FROM MemberGroup gr WHERE :member MEMBER OF gr.members ")
    List<MemberGroup> findMemberGroupsByMember(@Param("member") final Member member);
}
