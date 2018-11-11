package com.batrom.budgetcalculator.controller;

import com.batrom.budgetcalculator.dto.DebtDTO;
import com.batrom.budgetcalculator.service.DebtService;
import com.batrom.budgetcalculator.task.DebtTask;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CommonsLog
@RestController
@RequestMapping("/debt")
public class DebtRestController extends BaseRestController {

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<DebtDTO>> getAll(@RequestParam(name = "member") final String member) {
        return execute(member, debtService::getDebtsByMember);
    }

    @GetMapping(value = "/triggerTask")
    public ResponseEntity<HttpStatus> triggerStatus() {
        debtTask.fillOldData();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private final DebtService debtService;
    private final DebtTask debtTask;

    @Autowired
    public DebtRestController(final DebtService debtService, final DebtTask debtTask) {
        this.debtService = debtService;
        this.debtTask = debtTask;
    }
}
