package com.chess.logic.board;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardExtra {
    public static final boolean[] FIRST_FILE = initFile(0);
    public static final boolean[] SECOND_FILE = initFile(1);
    public static final boolean[] SEVENTH_FILE = initFile(6);
    public static final boolean[] EIGHTH_FILE = initFile(7);

    public static final boolean[] FIRST_ROW = initRow(0);
    public static final boolean[] SECOND_ROW = initRow(8);
    public static final boolean[] SEVENTH_ROW = initRow(48);
    public static final boolean[] EIGHTH_ROW = initRow(56);

    public static final String[] CHESS_NOTATION = initChessNotation();
    public static final Map<String, Integer> COORDINATE_NOTATION = initCoordinate();

    public static final int NUM_SQUARES = 64;
    public static final int NUM_SQUARES_PER_ROW = 8;

    private static String[] initChessNotation() {
        return new String[] {
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
        };
    }

    private static Map<String, Integer> initCoordinate() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();

        for (int i = 0; i < NUM_SQUARES; i++) {
            positionToCoordinate.put(CHESS_NOTATION[i], i);
        }

        return ImmutableMap.copyOf(positionToCoordinate);
    }


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

    public static int getCoordinateAtPosition(final String position) {
        return COORDINATE_NOTATION.get(position);
    }

    public static String getPositionAtCoordinate(final int coordinate) {
        return CHESS_NOTATION[coordinate];
    }
}
