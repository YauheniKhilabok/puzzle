package com.wix.test.core.impl;

import com.wix.test.core.BoardInitializer;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DefaultBoardInitializer implements BoardInitializer {

    private static final int SIZE = 4;
    private static final int EMPTY_TILE = 0;

    @Override
    public int[][] initialize() {
        int[][] board = new int[SIZE][SIZE];
        List<Integer> tiles = populateTiles();

        do {
            shuffleTiles(tiles);
        } while (!isSolvable(tiles));

        for (int i = 0; i < SIZE * SIZE; i++) {
            board[i / SIZE][i % SIZE] = tiles.get(i);
        }
        return board;
    }

    private List<Integer> populateTiles() {
        return IntStream.range(0, SIZE * SIZE)
            .boxed()
            .collect(Collectors.toList());
    }

    private void shuffleTiles(List<Integer> tiles) {
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < tiles.size(); i++) {
            int randomIndex = random.nextInt(tiles.size());
            Collections.swap(tiles, i, randomIndex);
        }
    }

    private boolean isSolvable(List<Integer> tiles) {
        long inversions = IntStream.range(0, tiles.size() - 1)
            .mapToLong(i -> IntStream.range(i + 1, tiles.size())
                .filter(j -> tiles.get(i) > tiles.get(j) && tiles.get(i) != EMPTY_TILE && tiles.get(j) != EMPTY_TILE)
                .count())
            .sum();

        return inversions % 2 == 0;
    }
}
