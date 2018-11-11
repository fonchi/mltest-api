package com.exercise.mltest.domain;

import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;

public class DnaStats {

    @Transient
    private int countMutantDna;
    @Transient
    private int countHumanDna;

    public DnaStats() {
    }

    public DnaStats(int countMutantDna, int countHumanDna) {
        this.countMutantDna = countMutantDna;
        this.countHumanDna = countHumanDna;
    }

    public int getCountMutantDna() {
        return countMutantDna;
    }

    public void setCountMutantDna(int countMutantDna) {
        this.countMutantDna = countMutantDna;
    }

    public int getCountHumanDna() {
        return countHumanDna;
    }

    public void setCountHumanDna(int countHumanDna) {
        this.countHumanDna = countHumanDna;
    }

    public float getMutantRatio() {
        if (countHumanDna <= 0)
            return 0;
        float ratio = countMutantDna / countHumanDna;
        return new BigDecimal(ratio).setScale(2, BigDecimal.ROUND_UP).floatValue();
    }

}
