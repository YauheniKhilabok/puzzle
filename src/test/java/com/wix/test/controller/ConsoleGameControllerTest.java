package com.wix.test.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.wix.test.core.Board;
import com.wix.test.input.InputService;
import com.wix.test.ui.BoardFormatter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ConsoleGameControllerTest {

    @Mock
    private InputService inputService;

    @Mock
    private Board board;

    @Mock
    private BoardFormatter boardFormatter;

    @InjectMocks
    private ConsoleGameController controller;

    private ByteArrayOutputStream outputStream;
    private PrintStream originalSystemOut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        outputStream = new ByteArrayOutputStream();
        originalSystemOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalSystemOut);
    }

    @Test
    void testPlay_gameSolved() {
        when(board.isSolved()).thenReturn(false, false, true);
        when(boardFormatter.format(board)).thenReturn("Formatted board");
        when(inputService.getUserInput()).thenReturn(1, 1, 0);

        controller.play();

        String output = outputStream.toString();
        assertTrue(output.contains("Congratulations! You've solved the puzzle."));
    }

    @Test
    void testPlay_invalidMove() {
        when(board.isSolved()).thenReturn(false, false);
        when(boardFormatter.format(board)).thenReturn("Formatted board");
        when(inputService.getUserInput()).thenReturn(1, 2, 0);
        when(board.moveTile(1)).thenReturn(false);
        when(board.moveTile(2)).thenReturn(true);

        controller.play();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid move! Try again."));
        assertTrue(output.contains("Thanks for playing!"));
    }
}
