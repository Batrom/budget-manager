package com.batrom.budgetcalculator.controller;

import com.batrom.budgetcalculator.dto.InitDataDTO;
import com.batrom.budgetcalculator.service.InitDataService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CommonsLog
@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseRestController {

    @GetMapping(value = "/getData")
    public ResponseEntity<InitDataDTO> getData() {
        return get(initDataService::getData);
    }

    private final InitDataService initDataService;

    @Autowired
    public ResourceController(final InitDataService initDataService) {
        this.initDataService = initDataService;
    }
}
