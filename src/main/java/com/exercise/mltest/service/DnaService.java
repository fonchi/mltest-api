package com.exercise.mltest.service;

import com.exercise.mltest.domain.Dna;
import com.exercise.mltest.domain.DnaStats;

public interface DnaService {

    public char[][] getMatrixDna(String[] dna);

    public boolean isMutant(char[][] dna);

    public Dna getDna(String[] dna);

    public Dna saveDna(Dna dna);

    public DnaStats getStats();

}
