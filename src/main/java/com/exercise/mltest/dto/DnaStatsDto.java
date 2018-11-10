package com.exercise.mltest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class DnaStatsDto {

    private static final long serialVersionUID = 6624726180748515507L;

    private float countMutantDna;
    private float countHumanDna;
    private float ratio;

    public DnaStatsDto() {
    }

    public DnaStatsDto(float countMutantDna, float countHumanDna) {
        this.countMutantDna = countMutantDna;
        this.countHumanDna = countHumanDna;
    }

    @JsonProperty("count_mutant_dna")
    public float getCountMutantDna() {
        return countMutantDna;
    }

    public void setCountMutantDna(float countMutantDna) {
        this.countMutantDna = countMutantDna;
    }

    @JsonProperty("count_human_dna")
    public float getCountHumanDna() {
        return countHumanDna;
    }

    public void setCountHumanDna(float countHumanDna) {
        this.countHumanDna = countHumanDna;
    }

    @JsonProperty("ratio")
    public float getRatio() {
        float ratio = countMutantDna / (countMutantDna + countHumanDna);
        BigDecimal bd = new BigDecimal(ratio);
        bd = bd.setScale(2, BigDecimal.ROUND_UP);
        return bd.floatValue();
    }
}
