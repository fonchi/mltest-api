package com.exercise.mltest.support;

/**
 * Support class for wrapping the recursive algorithm data passed by "reference"
 */
public class DnaData {

    private char[][] matrix;
    private int matchesAmount = 0;

    public DnaData(char[][] matrix) {
        this.matrix = matrix;
    }

    public char[][] getMatrix() {
        return matrix;
    }

    public int getMatchesAmount() {
        return matchesAmount;
    }

    public void incrementMatchesAmount() {
        this.matchesAmount++;
    }

}
