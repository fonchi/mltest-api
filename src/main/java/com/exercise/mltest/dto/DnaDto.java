package com.exercise.mltest.dto;

import javax.validation.constraints.NotNull;

public class DnaDto {

    private static final long serialVersionUID = -6624726180748515507L;

    @NotNull
    private String[] dna;

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }

}
