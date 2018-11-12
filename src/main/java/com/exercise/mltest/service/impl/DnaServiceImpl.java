package com.exercise.mltest.service.impl;

import com.exercise.mltest.domain.Dna;
import com.exercise.mltest.domain.DnaStats;
import com.exercise.mltest.enumeration.DnaTypeEnum;
import com.exercise.mltest.enumeration.IterateEnum;
import com.exercise.mltest.repository.DnaRepository;
import com.exercise.mltest.service.DnaService;
import com.exercise.mltest.support.DnaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DnaServiceImpl implements DnaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int LETTERS_NUM = 4;
    private final int SEQ_LIMIT = 1;
    private final String N_BASES = "ATCG";

    @Autowired
    DnaRepository dnaRepository;

    /**
     * Receives DNA array and returns it like NxN char matrix when it is valid
     *
     * @param dna
     * @return
     */
    @Override
    public char[][] getMatrixDna(String[] dna) {
        int dim = dna.length;
        if (dim == 0 || dim < LETTERS_NUM)
            return null;

        char[][] matrix = new char[dim][dim];
        for (int row = 0; row < dna.length; row++) {
            if (dna[row].length() != dim || !dna[row].matches("[" + N_BASES + "]+"))
                return null;
            char[] line = dna[row].toCharArray();
            for (int col = 0; col < line.length; col++) {
                matrix[row][col] = line[col];
            }
        }
        return matrix;
    }

    /**
     * Validates if DNA is mutant returning true
     *
     * @param dna
     * @return
     */
    @Override
    public boolean isMutant(char[][] dna) {
        return validateMutant(new DnaData(dna));
    }

    /**
     * Returns DNA stored in DB by DNA array
     *
     * @param dna
     * @return
     */
    @Override
    public Dna getDna(String[] dna) {
        return dnaRepository.findOneByDna(dna);
    }

    /**
     * Saves DNA in DB
     *
     * @param dna
     * @return
     */
    @Override
    public Dna saveDna(Dna dna) {
        return dnaRepository.save(dna);
    }

    /**
     * Returns DNA statistics based on stored DNAs
     *
     * @return
     */
    @Override
    public DnaStats getStats() {
        int mutantsAmount = dnaRepository.countByDnaType(DnaTypeEnum.MUTANT);
        int humansAmount = dnaRepository.countByDnaType(DnaTypeEnum.HUMAN);
        return new DnaStats(mutantsAmount, humansAmount);
    }

    /**
     * Algorithm that validates if a DNA is mutant
     *
     * @param data
     * @return
     */
    private boolean validateMutant(DnaData data) {
        char[][] matrix = data.getMatrix();
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                logger.debug("Validate row={}, column={}, letter={}", row, col, matrix[row][col]);
                countMatches(data, row, col);
                if (verifyLimitCondition(data))
                    return true;
            }
        }
        return false;
    }

    private void countMatches(DnaData data, int row, int col) {
        checkRightRow(data, row, col);
        if (verifyLimitCondition(data))
            return;
        checkDownColumn(data, row, col);
        if (verifyLimitCondition(data))
            return;
        checkRightDiagnonal(data, row, col);
        if (verifyLimitCondition(data))
            return;
        checkLeftDiagnonal(data, row, col);
        if (verifyLimitCondition(data))
            return;
    }

    private boolean verifyLimitCondition(DnaData data) {
        return data.getMatchesAmount() > SEQ_LIMIT ? true : false;
    }

    private void checkRightRow(DnaData data, int row, int col) {
        logger.debug("Checking right row with row={}, col={}", row, col + 1);
        validate(data, data.getMatrix()[row][col], row, col + 1, 1, IterateEnum.COL, 1);
    }

    private void checkDownColumn(DnaData data, int row, int col) {
        logger.debug("Checking down column with row={}, col={}", row + 1, col);
        validate(data, data.getMatrix()[row][col], row + 1, col, 1, IterateEnum.ROW, 1);
    }

    private void checkRightDiagnonal(DnaData data, int row, int col) {
        logger.debug("Checking right diagonal with row={}, col={}", row + 1, col + 1);
        validate(data, data.getMatrix()[row][col], row + 1, col + 1, 1, IterateEnum.DIAG, 1);
    }

    private void checkLeftDiagnonal(DnaData data, int row, int col) {
        logger.debug("Checking left diagonal row={}, col={}", row + 1, col - 1);
        validate(data, data.getMatrix()[row][col], row + 1, col - 1, -1, IterateEnum.DIAG, 1);
    }

    /**
     * Recursive algorithm for validation
     *
     * @param data
     * @param letter
     * @param row
     * @param col
     * @param direction
     * @param iterate
     * @param counter
     */
    private void validate(DnaData data, char letter, int row, int col, int direction, IterateEnum iterate, Integer counter) {

        logger.debug("Comparing letter={}, with row={}, col={}, direction={}, and iterate={}", letter, row, col, direction, iterate);
        if (col < 0 || row < 0 || col > data.getMatrix().length - 1 || row > data.getMatrix().length - 1 || data.getMatrix()[row][col] != letter)
            return;

        counter++;
        logger.debug("Letter " + letter + " is equal to " + data.getMatrix()[row][col] + " now counter is " + counter);

        if (counter == LETTERS_NUM) {
            data.incrementMatchesAmount();
            String str = new String(new char[LETTERS_NUM]).replace('\0', letter);
            logger.debug("Matches is {}. String = {}", data.getMatchesAmount(), str);
            return;
        }

        if (verifyLimitCondition(data)) {
            logger.debug("LIMIT MATCHES WAS REACH!");
            return;
        }

        if (iterate.equals(IterateEnum.ROW))
            validate(data, letter, row + direction, col, direction, iterate, counter);
        if (iterate.equals(IterateEnum.COL))
            validate(data, letter, row, col + direction, direction, iterate, counter);
        if (iterate.equals(IterateEnum.DIAG))
            validate(data, letter, row + 1, col + direction, direction, iterate, counter);
    }

}
