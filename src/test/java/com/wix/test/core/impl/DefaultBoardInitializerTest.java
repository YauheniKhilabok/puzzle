package com.wix.test.core.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;


class DefaultBoardInitializerTest {

    private DefaultBoardInitializer boardInitializer;

    @BeforeEach
    void setUp() {
        boardInitializer = new DefaultBoardInitializer();
    }

    @Test
    void initialize_shouldReturn4x4BoardWithUniqueValues_whenCalled() {
        int[][] board = boardInitializer.initialize();

        assertNotNull(board, "Board should not be null");
        assertEquals(4, board.length, "Board should have 4 rows");
        assertEquals(4, board[0].length, "Board should have 4 columns");

        Set<Integer> tiles = new HashSet<>();
        for (int[] row : board) {
            for (int tile : row) {
                tiles.add(tile);
            }
        }

        assertEquals(16, tiles.size(), "Board should contain 16 unique tiles");
        for (int i = 0; i < 16; i++) {
            assertTrue(tiles.contains(i), "Board should contain tile " + i);
        }
    }

    @Test
    void initialize_shouldReturnSolvableBoard_whenCalledMultipleTimes() {
        for (int i = 0; i < 10; i++) {
            int[][] board = boardInitializer.initialize();

            assertNotNull(board, "Board should not be null on initialization " + i);
            assertEquals(4, board.length, "Board should have 4 rows on initialization " + i);
            assertEquals(4, board[0].length, "Board should have 4 columns on initialization " + i);

            Set<Integer> tiles = new HashSet<>();
            for (int[] row : board) {
                for (int tile : row) {
                    tiles.add(tile);
                }
            }

            assertEquals(16, tiles.size(), "Board should contain 16 unique tiles on initialization " + i);
            for (int j = 0; j < 16; j++) {
                assertTrue(tiles.contains(j), "Board should contain tile " + j + " on initialization " + i);
            }
        }
    }
}
