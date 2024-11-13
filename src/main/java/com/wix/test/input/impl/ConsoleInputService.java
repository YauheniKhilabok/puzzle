package com.wix.test.input.impl;

import com.wix.test.input.InputService;
import java.util.Scanner;

public class ConsoleInputService implements InputService {

    private final Scanner scanner;
    private final String promptMessage;

    public ConsoleInputService(Scanner scanner, String promptMessage) {
        this.scanner = scanner;
        this.promptMessage = promptMessage != null ? promptMessage : "Enter tile number to move (or 0 to quit): ";
    }

    @Override
    public int getUserInput() {
        while (true) {
            System.out.print(promptMessage);
            try {
                if (scanner.hasNextInt()) {
                    return scanner.nextInt();
                } else {
                    System.out.println("Invalid input. Please enter an integer.");
                    scanner.next();
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again. Error: " + e.getMessage());
                scanner.next();
            }
        }
    }

    @Override
    public void close() {
        scanner.close();
    }
}
