package com.exercise.mltest.service.impl;

import com.exercise.mltest.domain.Dna;
import com.exercise.mltest.dto.DnaStatsDto;
import com.exercise.mltest.enumeration.DnaTypeEnum;
import com.exercise.mltest.repository.DnaRepository;
import com.exercise.mltest.service.IDnaService;
import com.exercise.mltest.support.DnaValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DnaService implements IDnaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DnaRepository dnaRepository;

    @Override
    public boolean isMutantV1(String[] dna) {
        return DnaValidator.isMutant(dna);
    }

    @Override
    public boolean isMutantV2(String[] dna) {
        Dna dnaEntity = getDna(dna);
        if (Objects.nonNull(dnaEntity))
            return dnaEntity.isMutant();

        boolean isMutant = DnaValidator.isMutant(dna);
        dnaEntity = new Dna();
        dnaEntity.setDna(dna);
        dnaEntity.setDnaType(isMutant ? DnaTypeEnum.MUTANT : DnaTypeEnum.HUMAN);
        saveDna(dnaEntity);

        return isMutant;
    }

    @Override
    public Dna getDna(String[] dna) {
        if (Objects.isNull(dna) || dna.length < 1)
            return null;
        return dnaRepository.findOneByDna(dna);
    }

    @Override
    public Dna saveDna(Dna dna) {
        return dnaRepository.save(dna);
    }

    @Override
    public DnaStatsDto getStats() {
        List<Dna> mutantDnas = dnaRepository.findByDnaType(DnaTypeEnum.MUTANT);
        List<Dna> humanDnas = dnaRepository.findByDnaType(DnaTypeEnum.HUMAN);

        return new DnaStatsDto(mutantDnas.size(), humanDnas.size());
    }
}
