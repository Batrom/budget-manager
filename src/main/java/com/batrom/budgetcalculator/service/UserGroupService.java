package com.batrom.budgetcalculator.service;

import com.batrom.budgetcalculator.model.UserGroup;
import com.batrom.budgetcalculator.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupService {
    @Autowired
    private UserGroupRepository userGroupRepository;

    public UserGroup findByName(final String name) {
        return userGroupRepository.findByName(name);
    }
}
