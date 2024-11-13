package com.wix.test.ui.impl;

import com.wix.test.core.Board;
import com.wix.test.ui.BoardFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PuzzleBoardFormatter implements BoardFormatter {

    @Override
    public String format(Board board) {
        if (board == null) {
            return "";
        }

        return Arrays.stream(board.getBoard())
            .map(row -> Arrays.stream(row)
                .mapToObj(tile -> tile == 0 ? "   " : String.format("%2d ", tile))
                .collect(Collectors.joining(" "))
            )
            .collect(Collectors.joining(System.lineSeparator()));
    }
}
