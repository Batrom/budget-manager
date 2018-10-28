package com.batrom.budgetcalculator.controller;

import com.batrom.budgetcalculator.dto.CommonDataDTO;
import com.batrom.budgetcalculator.service.CommonDataService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CommonsLog
@RestController
@RequestMapping("/common")
public class CommonDataRestController extends BaseRestController {

    @GetMapping(value = "/getData")
    public ResponseEntity<CommonDataDTO> getCommonData() {
        return get(commonDataService::getCommonData);
    }

    private final CommonDataService commonDataService;

    @Autowired
    public CommonDataRestController(final CommonDataService commonDataService) {
        this.commonDataService = commonDataService;
    }
}
