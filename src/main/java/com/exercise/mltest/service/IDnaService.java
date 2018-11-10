package com.exercise.mltest.service;

import com.exercise.mltest.domain.Dna;
import com.exercise.mltest.dto.DnaStatsDto;

public interface IDnaService {

    public boolean isMutantV1(String[] dna);

    public boolean isMutantV2(String[] dna);

    public Dna getDna(String[] dna);

    public Dna saveDna(Dna dna);

    public DnaStatsDto getStats();

}
