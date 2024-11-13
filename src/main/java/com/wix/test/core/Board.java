package com.wix.test.core;

public interface Board {

    int[][] getBoard();

    boolean isSolved();

    boolean moveTile(int tile);
}
