package com.exercise.mltest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DnaStatsDto {

    private static final long serialVersionUID = 6624726180748515507L;

    private int countMutantDna;
    private int countHumanDna;
    private float mutantRatio;

    public DnaStatsDto() {
    }

    public DnaStatsDto(int countMutantDna, int countHumanDna, float mutantRatio) {
        this.countMutantDna = countMutantDna;
        this.countHumanDna = countHumanDna;
        this.mutantRatio = mutantRatio;
    }

    @JsonProperty("count_mutant_dna")
    public int getCountMutantDna() {
        return countMutantDna;
    }

    public void setCountMutantDna(int countMutantDna) {
        this.countMutantDna = countMutantDna;
    }

    @JsonProperty("count_human_dna")
    public int getCountHumanDna() {
        return countHumanDna;
    }

    public void setCountHumanDna(int countHumanDna) {
        this.countHumanDna = countHumanDna;
    }

    @JsonProperty("ratio")
    public float getMutantRatio() {
        return mutantRatio;
    }

    public void setMutantRatio(float mutantRatio) {
        this.mutantRatio = mutantRatio;
    }

}
