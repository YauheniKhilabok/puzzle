package com.wix.test.input.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConsoleInputServiceTest {

    @Mock
    private Scanner mockScanner;

    private ConsoleInputService inputService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inputService = new ConsoleInputService(mockScanner, "Test prompt: ");
    }

    @Test
    void getUserInput_shouldReturnInteger_whenValidIntegerInputProvided() {
        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt()).thenReturn(5);

        int result = inputService.getUserInput();

        assertEquals(5, result);
        verify(mockScanner, times(1)).hasNextInt();
        verify(mockScanner, times(1)).nextInt();
    }

    @Test
    void getUserInput_shouldRetryOnInvalidInput_whenNonIntegerInputProvided() {
        when(mockScanner.hasNextInt()).thenReturn(false, true);
        when(mockScanner.next()).thenReturn("invalid");
        when(mockScanner.nextInt()).thenReturn(10);

        int result = inputService.getUserInput();

        assertEquals(10, result);
        verify(mockScanner, times(2)).hasNextInt();
        verify(mockScanner, times(1)).next();
        verify(mockScanner, times(1)).nextInt();
    }

    @Test
    void getUserInput_shouldHandleException_whenScannerThrowsException() {
        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt()).thenThrow(new RuntimeException("Test exception")).thenReturn(3);

        int result = inputService.getUserInput();

        assertEquals(3, result);
        verify(mockScanner, times(2)).hasNextInt();
        verify(mockScanner, times(2)).nextInt();
    }

    @Test
    void close_shouldCloseScanner_whenCalled() {
        inputService.close();

        verify(mockScanner, times(1)).close();
    }
}

