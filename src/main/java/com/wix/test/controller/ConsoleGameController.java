package com.wix.test.controller;

import com.wix.test.core.Board;
import com.wix.test.input.InputService;
import com.wix.test.ui.BoardFormatter;

public class ConsoleGameController {

    private final InputService inputService;
    private final Board board;
    private final BoardFormatter boardFormatter;
    private final String exitMessage;

    public ConsoleGameController(InputService inputService, Board board, BoardFormatter boardFormatter, String exitMessage) {
        this.inputService = inputService;
        this.board = board;
        this.boardFormatter = boardFormatter;
        this.exitMessage = exitMessage != null ? exitMessage : "Thanks for playing!";
    }

    public void play() {
        try (inputService) {
            while (!board.isSolved()) {
                System.out.println(boardFormatter.format(board));
                int tile = inputService.getUserInput();

                if (tile == 0) {
                    System.out.println(exitMessage);
                    break;
                }

                if (!board.moveTile(tile)) {
                    System.out.println("Invalid move! Try again.");
                }
            }
            if (board.isSolved()) {
                System.out.println("Congratulations! You've solved the puzzle.");
            }
        } catch (Exception e) {
            System.err.println("Error during the game: " + e.getMessage());
        }
    }
}
