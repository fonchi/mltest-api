package com.exercise.mltest.controller;

import com.exercise.mltest.dto.DnaDto;
import com.exercise.mltest.dto.DnaStatsDto;
import com.exercise.mltest.service.impl.DnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("dnas")
public class DnaController {

    @Autowired
    DnaService dnaService;

    @RequestMapping(value = "/mutant", method = RequestMethod.POST, headers = "X-API-Version=1")
    public ResponseEntity<?> isMutantDnaV1(@RequestBody @Valid DnaDto dnaDto) {

        if (dnaService.isMutantV1(dnaDto.getDna()))
            return ResponseEntity.ok("");

        return ResponseEntity.status(403).body("");
    }

    @RequestMapping(value = "/mutant", method = RequestMethod.POST, headers = "X-API-Version=2")
    public ResponseEntity<?> isMutantDnaV2(@RequestBody @Valid DnaDto dnaDto) {

        if (dnaService.isMutantV2(dnaDto.getDna()))
            return ResponseEntity.ok("");

        return ResponseEntity.status(403).body("");
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET, headers = "X-API-Version=2")
    public ResponseEntity<?> getDnaStats() {

        DnaStatsDto dto = dnaService.getStats();
        if (Objects.nonNull(dto))
            return ResponseEntity.ok(dto);

        return ResponseEntity.status(403).body("");
    }

}
