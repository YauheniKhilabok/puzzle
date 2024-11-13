package com.wix.test;

import com.wix.test.controller.ConsoleGameController;
import com.wix.test.core.Board;
import com.wix.test.core.BoardInitializer;
import com.wix.test.core.impl.DefaultBoardInitializer;
import com.wix.test.core.impl.PuzzleBoard;
import com.wix.test.input.InputService;
import com.wix.test.input.impl.ConsoleInputService;
import com.wix.test.ui.BoardFormatter;
import com.wix.test.ui.impl.PuzzleBoardFormatter;
import java.util.Scanner;

public class PuzzleGameApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (InputService inputService = new ConsoleInputService(scanner, "Enter tile number to move (or 0 to quit): ")) {
            BoardInitializer boardInitializer = new DefaultBoardInitializer();
            Board board = new PuzzleBoard(boardInitializer);
            BoardFormatter boardFormatter = new PuzzleBoardFormatter();
            ConsoleGameController consoleGameController = new ConsoleGameController(inputService, board, boardFormatter,
                "Game exited. Thanks for playing!");
            consoleGameController.play();
        } catch (Exception e) {
            System.out.println("Exceptions is happening:" + e.getMessage());
        }
    }
}
