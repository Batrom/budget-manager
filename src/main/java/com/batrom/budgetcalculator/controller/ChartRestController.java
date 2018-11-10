package com.batrom.budgetcalculator.controller;

import com.batrom.budgetcalculator.dto.DoughnutChartDTO;
import com.batrom.budgetcalculator.service.ChartService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CommonsLog
@RestController
@RequestMapping("/chart")
public class ChartRestController extends BaseRestController {

    @GetMapping(value = "/getData")
    public ResponseEntity<List<DoughnutChartDTO>> getData(@RequestParam("member") String member) {
        return execute(member, chartService::getData);
    }

    private final ChartService chartService;

    @Autowired
    public ChartRestController(final ChartService chartService) {
        this.chartService = chartService;
    }
}
