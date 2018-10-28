package com.batrom.budgetcalculator.service;

import com.batrom.budgetcalculator.dto.CategoryDTO;
import com.batrom.budgetcalculator.dto.CommonDataDTO;
import com.batrom.budgetcalculator.enums.Category;
import com.batrom.budgetcalculator.model.MemberGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CommonDataService {

    public CommonDataDTO getCommonData() {
        final CommonDataDTO commonDataDTO = new CommonDataDTO();
        commonDataDTO.setProductCategories(Stream.of(Category.values())
                                                 .map(this::categoryEnumToDTO)
                                                 .collect(Collectors.toList()));
        commonDataDTO.setMemberGroups(memberGroupService.findAll()
                                                        .stream()
                                                        .map(MemberGroup::getName)
                                                        .sorted((a, b) -> b.compareToIgnoreCase(a))
                                                        .collect(Collectors.toList()));
        return commonDataDTO;
    }

    private CategoryDTO categoryEnumToDTO(final Category categoryEnum) {
        final CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(categoryEnum.name());
        categoryDTO.setPolishName(categoryEnum.getPolishName());
        return categoryDTO;
    }

    private final MemberGroupService memberGroupService;

    @Autowired
    public CommonDataService(final MemberGroupService memberGroupService) {
        this.memberGroupService = memberGroupService;
    }
}
