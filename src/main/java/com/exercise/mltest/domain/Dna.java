package com.exercise.mltest.domain;

import com.exercise.mltest.enumeration.DnaTypeEnum;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Dna {

    @Id
    ObjectId _id;

    private String[] dna;
    private DnaTypeEnum dnaType;

    public Dna() {
    }

    public Dna(String[] dna, DnaTypeEnum dnaType) {
        this.dna = dna;
        this.dnaType = dnaType;
    }

    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }

    public DnaTypeEnum getDnaType() {
        return dnaType;
    }

    public void setDnaType(DnaTypeEnum dnaType) {
        this.dnaType = dnaType;
    }

    public boolean isMutant() {
        if (Objects.nonNull(this.dnaType))
            return this.dnaType.equals(DnaTypeEnum.MUTANT) ? true : false;
        return false;
    }

}
