package com.exercise.mltest.domain;

public class Dna {

    private char[][] matrix;
    private int amountMatches = 0;

    public Dna(char[][] matrix) {
        this.matrix = matrix;
    }

    public char[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(char[][] matrix) {
        this.matrix = matrix;
    }

    public int getAmountMatches() {
        return amountMatches;
    }

    public void setAmountMatches(int amountMatches) {
        this.amountMatches = amountMatches;
    }

    public void incrementAmountMatches() {
        this.amountMatches++;
    }

}
