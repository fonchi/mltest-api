package com.exercise.mltest;

import com.exercise.mltest.domain.Dna;
import com.exercise.mltest.domain.DnaStats;
import com.exercise.mltest.enumeration.DnaTypeEnum;
import com.exercise.mltest.repository.DnaRepository;
import com.exercise.mltest.service.DnaService;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DnaServiceTests {

    @Autowired
    private DnaService dnaService;

    @MockBean
    private DnaRepository dnaRepository;

    @Before
    public void setUp() {
        String[] dnaArr = {"AAAAGA", "CACCGC", "TTATGT", "AGAAGG", "ACCCTA", "TCACTG"};
        Dna dna = new Dna(dnaArr, DnaTypeEnum.MUTANT);
        Mockito.when(dnaRepository.findOneByDna(dna.getDna())).thenReturn(dna);

        String[] dnaArr2 = {"AAACGT", "CCCGTA", "GGGTAC", "TTTACG", "AAACGT", "CCCGTA"};
        Dna dna2 = new Dna(dnaArr2, DnaTypeEnum.MUTANT);
        dna2.set_id(new ObjectId());
        Mockito.when(dnaRepository.save(any(Dna.class))).thenReturn(dna2);

        int mutantAmount = 40;
        int humanAmount = 100;
        Mockito.when(dnaRepository.countByDnaType(DnaTypeEnum.MUTANT)).thenReturn(mutantAmount);
        Mockito.when(dnaRepository.countByDnaType(DnaTypeEnum.HUMAN)).thenReturn(humanAmount);
    }

    /**
     * getMatrixDna(String[] dna) success test
     */
    @Test
    public void whenValidDna_thenGetDnaShouldBeMatrixDna() {
        String[] dna = {"AAAA", "CACC", "TTAT", "AGAA"};
        char[][] validMatrix = {{'A', 'A', 'A', 'A'}, {'C', 'A', 'C', 'C'}, {'T', 'T', 'A', 'T'}, {'A', 'G', 'A', 'A'}};
        Assert.assertArrayEquals(validMatrix, dnaService.getMatrixDna(dna));
    }

    /**
     * getMatrixDna(String[] dna) fail test
     */
    @Test
    public void whenInvalidDna_thenGetDnaShouldBeNull() {
        String[] dna = {"AA", "CAQQ", "FTAT", "AGAA"};
        Assert.assertNull(dnaService.getMatrixDna(dna));
    }

    /**
     * isMutant(char[][] dna) success test
     */
    @Test
    public void whenMutantDna_thenIsMutantShouldBeTrue() {
        String[] mutant1 = {"AAAAGA", "CACCGC", "TTATGT", "AGAAGG", "ACCCTA", "TCACTG"};
        Assert.assertEquals(true, dnaService.isMutant(dnaService.getMatrixDna(mutant1)));
        String[] mutant2 = {"ACGTAC", "CGTACA", "GTACAC", "TACACG", "ACACGT", "CACGTA"};
        Assert.assertEquals(true, dnaService.isMutant(dnaService.getMatrixDna(mutant2)));
        String[] mutant3 = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        Assert.assertEquals(true, dnaService.isMutant(dnaService.getMatrixDna(mutant3)));
        String[] mutant4 = {"ATGCGA", "CGGTGC", "TCATGT", "AGCAGG", "CCCCTA", "TCACTG"};
        Assert.assertEquals(true, dnaService.isMutant(dnaService.getMatrixDna(mutant4)));
        String[] mutant5 = {"ATGCGA", "CGGTGT", "TAATTT", "AGCTGG", "CCTCTA", "CCCCTG"};
        Assert.assertEquals(true, dnaService.isMutant(dnaService.getMatrixDna(mutant5)));
        String[] mutant6 = {"AAACGT", "CCCGTA", "GGGTAC", "TTTACG", "AAACGT", "CCCGTA"};
        Assert.assertEquals(true, dnaService.isMutant(dnaService.getMatrixDna(mutant6)));
    }

    /**
     * isMutant(char[][] dna) fail test
     */
    @Test
    public void whenHumanDna_thenIsMutantShouldBeFalse() {
        String[] human1 = {"CGTACA", "CACGTA", "CGTACA", "ACACGT", "GTACAC", "TACACG"};
        Assert.assertEquals(false, dnaService.isMutant(dnaService.getMatrixDna(human1)));
        String[] human2 = {"CCCAGT", "GAGCTA", "TCTAAC", "AGACCA", "CTCGAC", "AAATCG"};
        Assert.assertEquals(false, dnaService.isMutant(dnaService.getMatrixDna(human2)));
    }

    /**
     * getDna() success test
     */
    @Test
    public void whenExistsDna_thenDnaShouldBeFound() {
        String[] dna = {"AAAAGA", "CACCGC", "TTATGT", "AGAAGG", "ACCCTA", "TCACTG"};
        Dna found = dnaService.getDna(dna);
        assertThat(found.getDna()).isEqualTo(dna);
    }

    /**
     * saveDna(Dna dna) success test
     */
    @Test
    public void whenDna_thenDnaShouldBeSaved() {
        String[] dnaArr = {"AAACGT", "CCCGTA", "GGGTAC", "TTTACG", "AAACGT", "CCCGTA"};
        Dna dna = new Dna();
        dna.setDna(dnaArr);
        dna.setDnaType(DnaTypeEnum.MUTANT);

        Dna savedDna = dnaService.saveDna(dna);
        Assert.assertNotNull(savedDna);
        Assert.assertNotNull(savedDna.get_id());
        assertThat(savedDna.getDna()).isEqualTo(dna.getDna());
        assertThat(savedDna.getDnaType()).isEqualTo(dna.getDnaType());
    }

    /**
     * getStats() success test
     */
    @Test
    public void whenGetDnaStats_thenShouldReturnStats() {
        DnaStats stats = new DnaStats(40, 100);
        DnaStats found = dnaService.getStats();
        assertThat(found.getCountMutantDna()).isEqualTo(stats.getCountMutantDna());
        assertThat(found.getCountHumanDna()).isEqualTo(stats.getCountHumanDna());
        assertThat(found.getMutantRatio()).isEqualTo(stats.getMutantRatio());
    }

}
