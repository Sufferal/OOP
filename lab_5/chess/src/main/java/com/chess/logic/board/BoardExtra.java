package com.chess.logic.board;

public class BoardExtra {
    public static final boolean[] FIRST_FILE = initFile(0);
    public static final boolean[] SECOND_FILE = initFile(1);
    public static final boolean[] SEVENTH_FILE = initFile(6);
    public static final boolean[] EIGHTH_FILE = initFile(7);

    public static final boolean[] SECOND_ROW = initRow(8);
    public static final boolean[] SEVENTH_ROW = initRow(48);

    public static final int NUM_SQUARES = 64;
    public static final int NUM_SQUARES_PER_ROW = 8;

    private static boolean[] initFile(int fileNum) {
        final boolean[] file = new boolean[NUM_SQUARES];

        do {
            file[fileNum] = true;
            fileNum += NUM_SQUARES_PER_ROW;
        } while(fileNum < NUM_SQUARES);

        return file;
    }

    private static boolean[] initRow(int rowNum) {
        final boolean[] row = new boolean[NUM_SQUARES];

        do {
            row[rowNum] = true;
            rowNum++;
        } while(rowNum % NUM_SQUARES_PER_ROW != 0);

        return row;
    }

    private BoardExtra() {
        throw new RuntimeException("You cannot instantiate this class");
    }

    public static boolean isValidSquareCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < NUM_SQUARES;
    }
}
