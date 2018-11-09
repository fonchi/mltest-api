package com.exercise.mltest.service.impl;

import com.exercise.mltest.domain.Dna;
import com.exercise.mltest.enumeration.IterateEnum;
import com.exercise.mltest.service.IDnaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DnaService implements IDnaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${mltest.dna.letters.number}")
    private int lettersNumber;
    @Value("${mltest.dna.sequences.limit}")
    private int sequenceLimit;
    @Value("${mltest.dna.n.bases}")
    private String nBases;

    @Override
    public boolean isMutant(String[] dna) {
        int dim = dna.length;
        if (dim == 0 || dim < lettersNumber)
            return false;

        char[][] matrix = new char[dim][dim];

//        JSONObject dnaJson = new JSONObject();
//        JSONArray jsonArray = new JSONArray();

        for (int row = 0; row < dna.length; row++) {
            if (dna[row].length() != dim || !dna[row].matches("[" + nBases + "]+"))
                return false;
//            jsonArray.add(row);
            char[] line = dna[row].toCharArray();
            for (int col = 0; col < line.length; col++) {
                matrix[row][col] = line[col];
            }
        }
//        dnaJson.put("dna", jsonArray);
        return validate(new Dna(matrix)/*, dnaJson*/);
    }

    private boolean validate(Dna dna) {
        char[][] matrix = dna.getMatrix();
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                System.out.println("Validate row=" + row + ", column=" + col + ", letter=" + matrix[row][col]);
                countMatches(dna, row, col);
                if (verifyLimitCondition(dna))
                    return true;
            }
        }
        return false;
    }

    private void countMatches(Dna dna, int row, int col) {
        checkRightRow(dna, row, col);
        if (verifyLimitCondition(dna))
            return;
        checkDownColumn(dna, row, col);
        if (verifyLimitCondition(dna))
            return;
        checkRightDiagnonal(dna, row, col);
        if (verifyLimitCondition(dna))
            return;
        checkLeftDiagnonal(dna, row, col);
        if (verifyLimitCondition(dna))
            return;
    }

    private boolean verifyLimitCondition(Dna dna) {
        return dna.getAmountMatches() > sequenceLimit ? true : false;
    }

    private void checkRightRow(Dna dna, int row, int col) {
        System.out.println("Checking right row with i=" + row + ", j=" + (col + 1));
        validate(dna, dna.getMatrix()[row][col], row, col + 1, 1, IterateEnum.COL, 1);
    }

    private void checkDownColumn(Dna dna, int row, int col) {
        System.out.println("Checking down column with i=" + row + ", j=" + (col + 1));
        validate(dna, dna.getMatrix()[row][col], row + 1, col, 1, IterateEnum.ROW, 1);
    }

    private void checkRightDiagnonal(Dna dna, int row, int col) {
        System.out.println("Checking right diagonal with i=" + (row + 1) + ", j=" + (col + 1));
        validate(dna, dna.getMatrix()[row][col], row + 1, col + 1, 1, IterateEnum.DIAG, 1);
    }

    private void checkLeftDiagnonal(Dna dna, int row, int col) {
        System.out.println("Checking left diagonal with i=" + (row - 1) + ", j=" + (col - 1));
        validate(dna, dna.getMatrix()[row][col], row + 1, col - 1, -1, IterateEnum.DIAG, 1);
    }

    private void validate(Dna dna, char letter, int row, int col, int direction, IterateEnum iterate, Integer counter) {

        System.out.println("Comparing letter=" + letter + ", with i=" + row + ", j=" + col + ", direction=" + direction + ", iterate=" + iterate);
        if (counter == lettersNumber || col < 0 || row < 0 || col > dna.getMatrix().length - 1 || row > dna.getMatrix().length - 1 || dna.getMatrix()[row][col] != letter)
            return;

        counter++;
        System.out.println("Letter " + letter + " is equal to " + dna.getMatrix()[row][col] + " now counter is " + counter);

        if (counter == lettersNumber) {
            dna.incrementAmountMatches();
            String str = new String(new char[lettersNumber]).replace('\0', letter);
            System.out.println("Matches is " + dna.getAmountMatches() + ". String = " + str);
            return;
        }

        if (verifyLimitCondition(dna)) {
            System.out.println("LIMIT MATCHES WAS REACH!");
            return;
        }

        if (iterate.equals(IterateEnum.ROW))
            validate(dna, letter, row + direction, col, direction, iterate, counter);
        if (iterate.equals(IterateEnum.COL))
            validate(dna, letter, row, col + direction, direction, iterate, counter);
        if (iterate.equals(IterateEnum.DIAG))
            validate(dna, letter, row + 1, col + direction, direction, iterate, counter);
    }

}
