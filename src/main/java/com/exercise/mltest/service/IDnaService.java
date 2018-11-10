package com.exercise.mltest.service;

import com.exercise.mltest.domain.Dna;

public interface IDnaService {

    public boolean isMutant(String[] dna);

    public Dna getDna(String[] dna);

    public Dna saveDna(Dna dna);

}
