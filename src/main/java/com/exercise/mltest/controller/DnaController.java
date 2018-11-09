package com.exercise.mltest.controller;

import com.exercise.mltest.dto.DnaDto;
import com.exercise.mltest.service.impl.DnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("dnas")
public class DnaController {

    @Autowired
    DnaService dnaService;

    @RequestMapping(value = "/mutant", method = RequestMethod.POST)
    public ResponseEntity<?> isMutant(@RequestBody @Valid DnaDto dnaDto) {

        if (dnaService.isMutant(dnaDto.getDna()))
            return ResponseEntity.ok("");

        return ResponseEntity.status(403).body("");
    }

}
