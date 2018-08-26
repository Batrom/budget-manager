package com.batrom.budgetcalculator.service;

import com.batrom.budgetcalculator.dto.CategoryDTO;
import com.batrom.budgetcalculator.dto.InitDataDTO;
import com.batrom.budgetcalculator.enums.Category;
import com.batrom.budgetcalculator.model.MemberGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class InitDataService {

    public InitDataDTO getData() {
        final InitDataDTO initDataDTO = new InitDataDTO();
        initDataDTO.setProductCategories(Stream.of(Category.values())
                                               .map(this::categoryEnumToDTO)
                                               .collect(Collectors.toList()));
        initDataDTO.setMemberGroups(memberGroupService.findAll()
                                                  .stream()
                                                  .map(MemberGroup::getName)
                                                  .sorted((a, b) -> b.compareToIgnoreCase(a))
                                                  .collect(Collectors.toList()));
        return initDataDTO;
    }

    private CategoryDTO categoryEnumToDTO(final Category categoryEnum) {
        final CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(categoryEnum.name());
        categoryDTO.setPolishName(categoryEnum.getPolishName());
        return categoryDTO;
    }

    private final MemberGroupService memberGroupService;

    @Autowired
    public InitDataService(final MemberGroupService memberGroupService) {
        this.memberGroupService = memberGroupService;
    }
}
