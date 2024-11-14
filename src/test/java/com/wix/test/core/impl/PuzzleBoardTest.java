package com.wix.test.core.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.wix.test.core.BoardInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PuzzleBoardTest {

    private PuzzleBoard puzzleBoard;
    private BoardInitializer mockInitializer;

    @BeforeEach
    void setUp() {
        mockInitializer = mock(BoardInitializer.class);

        int[][] initialBoard = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
        };
        when(mockInitializer.initialize()).thenReturn(initialBoard);

        puzzleBoard = new PuzzleBoard(mockInitializer);
    }

    @Test
    void getBoard_shouldReturnInitializedBoard_whenCalled() {
        int[][] board = puzzleBoard.getBoard();

        assertNotNull(board, "Board should not be null");
        assertEquals(4, board.length, "Board should have 4 rows");
        assertEquals(4, board[0].length, "Board should have 4 columns");
        assertArrayEquals(mockInitializer.initialize(), board, "Board should match initialized board state");
    }

    @Test
    void isSolved_shouldReturnTrue_whenBoardIsInSolvedState() {
        boolean solved = puzzleBoard.isSolved();

        assertTrue(solved, "Board should be solved");
    }

    @Test
    void isSolved_shouldReturnFalse_whenBoardIsNotInSolvedState() {
        int[][] unsolvedBoard = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 15, 14, 0}
        };
        when(mockInitializer.initialize()).thenReturn(unsolvedBoard);
        puzzleBoard = new PuzzleBoard(mockInitializer);

        boolean solved = puzzleBoard.isSolved();

        assertFalse(solved, "Board should not be solved");
    }

    @Test
    void moveTile_shouldReturnTrue_whenTileIsAdjacentToEmptySpace() {
        int tileToMove = 15;

        boolean moved = puzzleBoard.moveTile(tileToMove);

        assertTrue(moved, "Move should be successful for tile adjacent to the empty space");
        assertEquals(15, puzzleBoard.getBoard()[3][3], "Tile 15 should have moved to the empty position");
        assertEquals(0, puzzleBoard.getBoard()[3][2], "Empty tile should have moved to the original position of tile 15");
    }

    @Test
    void moveTile_shouldReturnFalse_whenTileIsNotAdjacentToEmptySpace() {
        int tileToMove = 11;

        boolean moved = puzzleBoard.moveTile(tileToMove);

        assertFalse(moved, "Move should fail for tile not adjacent to the empty space");
    }

    @Test
    void moveTile_shouldNotAffectSolvedState_whenNonAdjacentTileIsAttemptedToMove() {
        int[][] solvedBoard = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
        };
        when(mockInitializer.initialize()).thenReturn(solvedBoard);
        puzzleBoard = new PuzzleBoard(mockInitializer);

        boolean moved = puzzleBoard.moveTile(11);
        boolean isStillSolved = puzzleBoard.isSolved();

        assertFalse(moved, "Move should fail for non-adjacent tile");
        assertTrue(isStillSolved, "Board should remain in solved state");
    }
}

