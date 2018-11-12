package com.exercise.mltest.controller;

import com.exercise.mltest.domain.Dna;
import com.exercise.mltest.domain.DnaStats;
import com.exercise.mltest.dto.DnaDto;
import com.exercise.mltest.dto.DnaStatsDto;
import com.exercise.mltest.enumeration.DnaTypeEnum;
import com.exercise.mltest.service.DnaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DnaService dnaService;

    private String invalidDna = "Dna format is invalid. " +
            "Please, make sure it has NxN dimension and contains allowed characters.";

    /**
     * Endpoint for excercise level 2.
     * That receives a DNA and then verifies if it is mutant
     *
     * @param dnaDto
     * @return
     */
    @RequestMapping(value = "/mutant", method = RequestMethod.POST, headers = "X-API-Version=1")
    public ResponseEntity<?> mutantValidationVersion1(@RequestBody @Valid DnaDto dnaDto) {

        logger.debug("Getting matrix DNA from strings array");
        char[][] matrixDna = dnaService.getMatrixDna(dnaDto.getDna());
        if (Objects.isNull(matrixDna)) {
            logger.debug("DNA contains invalid format");
            return ResponseEntity.status(400).body(invalidDna);
        }
        if (dnaService.isMutant(matrixDna)) {
            logger.debug("DNA is mutant");
            return ResponseEntity.ok("");
        }
        logger.debug("DNA is human");
        return ResponseEntity.status(403).body("");
    }

    /**
     * Endpoint for excercise level 3.
     * That receives a DNA and verifies if it is mutant, previously verifying if it exists persisted in database
     *
     * @param dnaDto
     * @return
     */
    @RequestMapping(value = "/mutant", method = RequestMethod.POST, headers = "X-API-Version=2")
    public ResponseEntity<?> mutantValidationVersion2(@RequestBody @Valid DnaDto dnaDto) {

        logger.debug("Searching if DNA was saved in DB");
        Dna dna = dnaService.getDna(dnaDto.getDna());
        if (Objects.isNull(dna)) {
            logger.debug("DNA wasn't in DB. Getting matrix DNA from strings array");
            char[][] matrixDna = dnaService.getMatrixDna(dnaDto.getDna());
            if (Objects.isNull(matrixDna)) {
                logger.debug("DNA contains invalid format");
                return ResponseEntity.status(400).body(invalidDna);
            }
            logger.debug("Saving validated DNA in DB");
            dna = dnaService.saveDna(new Dna(dnaDto.getDna(), dnaService.isMutant(matrixDna) ?
                    DnaTypeEnum.MUTANT : DnaTypeEnum.HUMAN));
        }
        if (dna.isMutant()) {
            logger.debug("DNA is mutant");
            return ResponseEntity.ok("");
        }
        logger.debug("DNA is human");
        return ResponseEntity.status(403).body("");
    }

    /**
     * Endpoint for excercise level 3
     * That returns count of mutant and human DNAs stored in database and proportion of mutant DNAs respect to human DNAs
     *
     * @return dnaStatsDto
     */
    @RequestMapping(value = "/stats", method = RequestMethod.GET, headers = "X-API-Version=2")
    public ResponseEntity<?> getDnaStats() {

        logger.debug("Getting DNA stats");
        DnaStats dnaStats = dnaService.getStats();
        if (Objects.nonNull(dnaStats))
            return ResponseEntity.ok(new DnaStatsDto(dnaStats.getCountMutantDna(),
                    dnaStats.getCountHumanDna(), dnaStats.getMutantRatio()));
        return ResponseEntity.status(403).body("");
    }

}
