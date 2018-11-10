package com.exercise.mltest.repository;

import com.exercise.mltest.domain.Dna;
import com.exercise.mltest.enumeration.DnaTypeEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DnaRepository extends MongoRepository<Dna, String> {

    public Dna findOneByDna(String[] dna);

    public List<Dna> findByDnaType(DnaTypeEnum dnaType);

}
