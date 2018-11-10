package com.exercise.mltest.support;

public class DnaData {

    private char[][] matrix;
    private int matchesAmount = 0;

    public DnaData(char[][] matrix) {
        this.matrix = matrix;
    }

    public char[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(char[][] matrix) {
        this.matrix = matrix;
    }

    public int getMatchesAmount() {
        return matchesAmount;
    }

    public void setMatchesAmount(int matchesAmount) {
        this.matchesAmount = matchesAmount;
    }

    public void incrementMatchesAmount() {
        this.matchesAmount++;
    }

}
