package com.exercise.mltest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class DnaStatsDto {

    private static final long serialVersionUID = 6624726180748515507L;

    private int countMutantDna;
    private int countHumanDna;
    private float ratio;

    public DnaStatsDto() {
    }

    public DnaStatsDto(int countMutantDna, int countHumanDna) {
        this.countMutantDna = countMutantDna;
        this.countHumanDna = countHumanDna;
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
    public float getRatio() {
        if (countHumanDna <= 0)
            return 0;
        float ratio = countMutantDna / countHumanDna;
        return new BigDecimal(ratio).setScale(2, BigDecimal.ROUND_UP).floatValue();
    }
}
