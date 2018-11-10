package com.exercise.mltest;

import com.exercise.mltest.support.DnaValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DnaValidatorTests {

    @Test
    public void testIsMutantWithMutantDnas() {
        String[] mutant1 = {"AAAAGA", "CACCGC", "TTATGT", "AGAAGG", "ACCCTA", "TCACTG"};
        Assert.assertEquals(true, DnaValidator.isMutant(mutant1));
        String[] mutant2 = {"AAAAGA", "CACCGC", "TTATGT", "AGAAGG", "ACCCTA", "TCACTG"};
        Assert.assertEquals(true, DnaValidator.isMutant(mutant2));
        String[] mutant3 = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        Assert.assertEquals(true, DnaValidator.isMutant(mutant3));
        String[] mutant4 = {"ATGCGA", "CGGTGC", "TCATGT", "AGCAGG", "CCCCTA", "TCACTG"};
        Assert.assertEquals(true, DnaValidator.isMutant(mutant4));
        String[] mutant5 = {"ATGCGA", "CGGTGT", "TAATTT", "AGCTGG", "CCTCTA", "CCCCTG"};
        Assert.assertEquals(true, DnaValidator.isMutant(mutant5));
        String[] mutant6 = {"AAACGT", "CCCGTA", "GGGTAC", "TTTACG", "AAACGT", "CCCGTA"};
        Assert.assertEquals(true, DnaValidator.isMutant(mutant6));
        String[] mutant7 = {"ACGTAC", "CGTACA", "GTACAC", "TACACG", "ACACGT", "CACGTA"};
        Assert.assertEquals(true, DnaValidator.isMutant(mutant7));
    }

    @Test
    public void testIsMutantWithHumanDnas() {
        String[] human1 = {"CGTACA", "CACGTA", "CGTACA", "ACACGT", "GTACAC", "TACACG"};
        Assert.assertEquals(false, DnaValidator.isMutant(human1));
        String[] human2 = {"CCCAGT", "GAGCTA", "TCTAAC", "AGACCA", "CTCGAC", "AAATCG"};
        Assert.assertEquals(false, DnaValidator.isMutant(human2));
    }


}
