package com.exercise.mltest.dto;

import javax.validation.constraints.NotNull;

public class DnaRequestDto {

    @NotNull
    private String[] dna;

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }

}
