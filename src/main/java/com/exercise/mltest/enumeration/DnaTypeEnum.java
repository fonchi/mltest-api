package com.exercise.mltest.enumeration;

public enum DnaTypeEnum {
    MUTANT("Mutant"),
    HUMAN("Human");

    private String value;

    private DnaTypeEnum(String value) {

        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DnaTypeEnum getEnum(String value) {
        for (DnaTypeEnum enumeration : DnaTypeEnum.values()) {
            if (enumeration.name().equals(value)) {
                return enumeration;
            }
        }
        return null;
    }
}
