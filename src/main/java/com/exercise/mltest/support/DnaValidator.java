package com.exercise.mltest.support;

import com.exercise.mltest.enumeration.IterateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DnaValidator {

    private static final Logger logger = LoggerFactory.getLogger(DnaValidator.class);

    private static int LETTERS_NUM = 4;
    private static int SEQ_LIMIT = 1;
    private static String N_BASES = "ATCG";

    public static boolean isMutant(String[] dna) {
        int dim = dna.length;
        if (dim == 0 || dim < LETTERS_NUM)
            return false;

        char[][] matrix = new char[dim][dim];

        for (int row = 0; row < dna.length; row++) {
            if (dna[row].length() != dim || !dna[row].matches("[" + N_BASES + "]+"))
                return false;
            char[] line = dna[row].toCharArray();
            for (int col = 0; col < line.length; col++) {
                matrix[row][col] = line[col];
            }
        }
        return validate(new DnaData(matrix));
    }

    private static boolean validate(DnaData data) {
        char[][] matrix = data.getMatrix();
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                logger.debug("Validate row={}, column={}, letter={}", row, col, matrix[row][col]);
                countMatches(data, row, col);
                if (verifyLimitCondition(data)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void countMatches(DnaData data, int row, int col) {
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

    private static boolean verifyLimitCondition(DnaData data) {
        return data.getMatchesAmount() > SEQ_LIMIT ? true : false;
    }

    private static void checkRightRow(DnaData data, int row, int col) {
        logger.debug("Checking right row with row={}, col={}", row, col + 1);
        validate(data, data.getMatrix()[row][col], row, col + 1, 1, IterateEnum.COL, 1);
    }

    private static void checkDownColumn(DnaData data, int row, int col) {
        logger.debug("Checking down column with row={}, col={}", row + 1, col);
        validate(data, data.getMatrix()[row][col], row + 1, col, 1, IterateEnum.ROW, 1);
    }

    private static void checkRightDiagnonal(DnaData data, int row, int col) {
        logger.debug("Checking right diagonal with row={}, col={}", row + 1, col + 1);
        validate(data, data.getMatrix()[row][col], row + 1, col + 1, 1, IterateEnum.DIAG, 1);
    }

    private static void checkLeftDiagnonal(DnaData data, int row, int col) {
        logger.debug("Checking left diagonal row={}, col={}", row + 1, col - 1);
        validate(data, data.getMatrix()[row][col], row + 1, col - 1, -1, IterateEnum.DIAG, 1);
    }

    private static void validate(DnaData data, char letter, int row, int col, int direction, IterateEnum iterate, Integer counter) {

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
