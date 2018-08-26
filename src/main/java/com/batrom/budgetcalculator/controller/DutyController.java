package com.batrom.budgetcalculator.controller;

import com.batrom.budgetcalculator.dto.DutyDTO;
import com.batrom.budgetcalculator.service.DutyService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CommonsLog
@RestController
@RequestMapping("/duty")
public class DutyController extends BaseRestController {

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<DutyDTO>> getAll() {
        return get(dutyService::getAll);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<DutyDTO> save(@RequestBody final DutyDTO dutyDTO) {
        return execute(dutyDTO, dutyService::save);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<DutyDTO> delete(@RequestBody final DutyDTO dutyDTO) {
        return execute(dutyDTO, dutyService::delete);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<DutyDTO> updateDuty(@RequestBody final DutyDTO dutyDTO) {
        return execute(dutyDTO, dutyService::update);
    }

    private final DutyService dutyService;

    @Autowired
    public DutyController(final DutyService dutyService) {
        this.dutyService = dutyService;
    }
}
