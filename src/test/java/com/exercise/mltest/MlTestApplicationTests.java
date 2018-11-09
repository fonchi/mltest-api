package com.exercise.mltest;

import com.exercise.mltest.service.IDnaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MlTestApplicationTests {

    @Autowired
    IDnaService mutantService;

    @Test
    public void testIsMutant() {
        //valid dna's
        String[] mutant1 = {"AAAAGA", "CACCGC", "TTATGT", "AGAAGG", "ACCCTA", "TCACTG"};
        Assert.assertEquals(true, mutantService.isMutant(mutant1));
        String[] mutant2 = {"AAAAGA", "CACCGC", "TTATGT", "AGAAGG", "ACCCTA", "TCACTG"};
        Assert.assertEquals(true, mutantService.isMutant(mutant2));
        String[] mutant3 = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        Assert.assertEquals(true, mutantService.isMutant(mutant3));
        String[] mutant4 = {"ATGCGA", "CGGTGC", "TCATGT", "AGCAGG", "CCCCTA", "TCACTG"};
        Assert.assertEquals(true, mutantService.isMutant(mutant4));
        String[] mutant5 = {"ATGCGA", "CGGTGT", "TAATTT", "AGCTGG", "CCTCTA", "CCCCTG"};
        Assert.assertEquals(true, mutantService.isMutant(mutant5));
        String[] mutant6 = {"AAACGT", "CCCGTA", "GGGTAC", "TTTACG", "AAACGT", "CCCGTA"};
        Assert.assertEquals(true, mutantService.isMutant(mutant6));
        String[] mutant7 = {"ACGTAC", "CGTACA", "GTACAC", "TACACG", "ACACGT", "CACGTA"};
        Assert.assertEquals(true, mutantService.isMutant(mutant7));

        //invalid dna's
        String[] human1 = {"CGTACA", "CACGTA", "CGTACA", "ACACGT", "GTACAC", "TACACG"};
        Assert.assertEquals(false, mutantService.isMutant(human1));
        String[] human2 = {"CCCAGT", "GAGCTA", "TCTAAC", "AGACCA", "CTCGAC", "AAATCG"};
        Assert.assertEquals(false, mutantService.isMutant(human2));
    }

}
