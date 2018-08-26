package com.batrom.budgetcalculator.controller;

import com.batrom.budgetcalculator.dto.DebtDTO;
import com.batrom.budgetcalculator.service.DebtService;
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
@RequestMapping("/debt")
public class DebtController extends BaseRestController {

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<DebtDTO>> getAll(@RequestParam(name = "member") final String member) {
        return get(member, debtService::getDebtsByMember);
    }

    private final DebtService debtService;

    @Autowired
    public DebtController(final DebtService debtService) {
        this.debtService = debtService;
    }
}
