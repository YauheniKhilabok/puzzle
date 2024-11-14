package com.wix.test.ui.impl;

import com.wix.test.core.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PuzzleBoardFormatterTest {

    private PuzzleBoardFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = new PuzzleBoardFormatter();
    }

    @Test
    void format_shouldReturnEmptyString_whenBoardIsNull() {
        // when
        String result = formatter.format(null);

        // then
        assertEquals("", result);
    }

    @Test
    void format_shouldReturnFormattedString_whenBoardIsNotNull() {
        // given
        Board mockBoard = mock(Board.class);
        int[][] boardArray = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0} // Assuming 0 is the empty tile
        };
        when(mockBoard.getBoard()).thenReturn(boardArray);

        // when
        String result = formatter.format(mockBoard);

        // then
        String expectedOutput =
            """
                 1   2   3   4\s
                 5   6   7   8\s
                 9  10  11  12\s
                13  14  15    \s""";
        assertEquals(expectedOutput, result);
    }

    @Test
    void format_shouldFormatWithEmptyTileInPlace_whenEmptyTileIsInMiddle() {
        // given
        Board mockBoard = mock(Board.class);
        int[][] boardArray = {
            {1, 2, 3, 4},
            {5, 0, 7, 8},   // Empty tile in the middle
            {9, 10, 11, 12},
            {13, 14, 15, 6}
        };
        when(mockBoard.getBoard()).thenReturn(boardArray);

        // when
        String result = formatter.format(mockBoard);

        // then
        String expectedOutput =
            """
                 1   2   3   4\s
                 5       7   8\s
                 9  10  11  12\s
                13  14  15   6\s""";
        assertEquals(expectedOutput, result);
    }
}
