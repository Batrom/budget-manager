package com.batrom.budgetcalculator.service;

import com.batrom.budgetcalculator.model.Member;
import com.batrom.budgetcalculator.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    public Member findByName(final String name) {
        return memberRepository.findByName(name);
    }

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
