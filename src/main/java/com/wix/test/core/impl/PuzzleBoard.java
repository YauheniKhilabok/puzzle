package com.wix.test.core.impl;

import com.wix.test.core.Board;
import com.wix.test.core.BoardInitializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class PuzzleBoard implements Board {

    private static final int SIZE = 4;
    private static final int EMPTY_TILE = 0;

    private final int[][] board;
    private final Map<Integer, int[]> tilePositions;
    private int emptyRow;
    private int emptyCol;

    public PuzzleBoard(BoardInitializer boardInitializer) {
        this.board = boardInitializer.initialize();
        this.tilePositions = new HashMap<>();
        setTilePositions();
    }

    @Override
    public int[][] getBoard() {
        return board;
    }

    @Override
    public boolean isSolved() {
        return IntStream.range(0, SIZE * SIZE - 1)
            .allMatch(i -> board[i / SIZE][i % SIZE] == i + 1)
            && board[SIZE - 1][SIZE - 1] == EMPTY_TILE;
    }

    @Override
    public boolean moveTile(int tile) {
        return findTilePosition(tile).map(tilePos -> {
            int tileRow = tilePos[0];
            int tileCol = tilePos[1];
            if (isAdjacentToEmpty(tileRow, tileCol)) {
                swapWithEmpty(tileRow, tileCol);
                return true;
            }
            return false;
        }).orElse(false);
    }

    private Optional<int[]> findTilePosition(int tile) {
        return Optional.ofNullable(tilePositions.get(tile));
    }

    private boolean isAdjacentToEmpty(int tileRow, int tileCol) {
        return (Math.abs(tileRow - emptyRow) == 1 && tileCol == emptyCol) ||
            (Math.abs(tileCol - emptyCol) == 1 && tileRow == emptyRow);
    }

    private void swapWithEmpty(int tileRow, int tileCol) {
        int tile = board[tileRow][tileCol];
        board[emptyRow][emptyCol] = tile;
        board[tileRow][tileCol] = EMPTY_TILE;

        tilePositions.put(tile, new int[]{emptyRow, emptyCol});
        tilePositions.put(EMPTY_TILE, new int[]{tileRow, tileCol});
        emptyRow = tileRow;
        emptyCol = tileCol;
    }

    private void setTilePositions() {
        IntStream.range(0, SIZE).forEach(row ->
            IntStream.range(0, SIZE).forEach(col -> {
                int tile = board[row][col];
                tilePositions.put(tile, new int[]{row, col});
                if (tile == EMPTY_TILE) {
                    emptyRow = row;
                    emptyCol = col;
                }
            })
        );
    }
}

