package com.exercise.mltest.enumeration;

/**
 * Enumeration to define DNA type
 */
public enum DnaTypeEnum {
    MUTANT("Mutant"),
    HUMAN("Human");

    private String value;

    private DnaTypeEnum(String value) {
        this.value = value;
    }
}
