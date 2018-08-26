package com.batrom.budgetcalculator.service;

import com.batrom.budgetcalculator.model.Member;
import com.batrom.budgetcalculator.model.MemberGroup;
import com.batrom.budgetcalculator.repository.MemberGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberGroupService {

    public MemberGroup findByName(final String name) {
        return memberGroupRepository.findByName(name);
    }

    public List<MemberGroup> findAll() {
        return memberGroupRepository.findAll();
    }

    public List<MemberGroup> findMemberGroupsByMember(final Member member) {
        return memberGroupRepository.findMemberGroupsByMember(member);
    }

    private final MemberGroupRepository memberGroupRepository;

    @Autowired
    public MemberGroupService(final MemberGroupRepository memberGroupRepository) {
        this.memberGroupRepository = memberGroupRepository;
    }
}
