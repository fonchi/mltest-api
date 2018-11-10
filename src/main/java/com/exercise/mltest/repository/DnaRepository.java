package com.exercise.mltest.repository;

import com.exercise.mltest.domain.Dna;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DnaRepository extends MongoRepository<Dna, String> {

    public Dna findOneByDna(String[] dna);

}
