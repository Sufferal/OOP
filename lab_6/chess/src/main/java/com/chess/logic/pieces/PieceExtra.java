package com.chess.logic.pieces;

import com.chess.logic.Color;
import com.chess.logic.board.BoardExtra;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

enum PieceExtra {

    INSTANCE;

    private final Table<Color, Integer, Queen> ALL_POSSIBLE_QUEENS = PieceExtra.createAllPossibleMovedQueens();
    private final Table<Color, Integer, Rook> ALL_POSSIBLE_ROOKS = PieceExtra.createAllPossibleMovedRooks();
    private final Table<Color, Integer, Knight> ALL_POSSIBLE_KNIGHTS = PieceExtra.createAllPossibleMovedKnights();
    private final Table<Color, Integer, Bishop> ALL_POSSIBLE_BISHOPS = PieceExtra.createAllPossibleMovedBishops();
    private final Table<Color, Integer, Pawn> ALL_POSSIBLE_PAWNS = PieceExtra.createAllPossibleMovedPawns();

    Pawn getMovedPawn(final Color color,
                      final int destinationCoordinate) {
        return ALL_POSSIBLE_PAWNS.get(color, destinationCoordinate);
    }

    Knight getMovedKnight(final Color color,
                          final int destinationCoordinate) {
        return ALL_POSSIBLE_KNIGHTS.get(color, destinationCoordinate);
    }

    Bishop getMovedBishop(final Color color,
                          final int destinationCoordinate) {
        return ALL_POSSIBLE_BISHOPS.get(color, destinationCoordinate);
    }

    Rook getMovedRook(final Color color,
                      final int destinationCoordinate) {
        return ALL_POSSIBLE_ROOKS.get(color, destinationCoordinate);
    }

    Queen getMovedQueen(final Color color,
                        final int destinationCoordinate) {
        return ALL_POSSIBLE_QUEENS.get(color, destinationCoordinate);
    }

    private static Table<Color, Integer, Pawn> createAllPossibleMovedPawns() {
        final ImmutableTable.Builder<Color, Integer, Pawn> pieces = ImmutableTable.builder();
        for(final Color color : Color.values()) {
            for(int i = 0; i < BoardExtra.SQUARES_NUM; i++) {
                pieces.put(color, i, new Pawn(color, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<Color, Integer, Knight> createAllPossibleMovedKnights() {
        final ImmutableTable.Builder<Color, Integer, Knight> pieces = ImmutableTable.builder();
        for(final Color color : Color.values()) {
            for(int i = 0; i < BoardExtra.SQUARES_NUM; i++) {
                pieces.put(color, i, new Knight(color, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<Color, Integer, Bishop> createAllPossibleMovedBishops() {
        final ImmutableTable.Builder<Color, Integer, Bishop> pieces = ImmutableTable.builder();
        for(final Color color : Color.values()) {
            for(int i = 0; i < BoardExtra.SQUARES_NUM; i++) {
                pieces.put(color, i, new Bishop(color, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<Color, Integer, Rook> createAllPossibleMovedRooks() {
        final ImmutableTable.Builder<Color, Integer, Rook> pieces = ImmutableTable.builder();
        for(final Color color : Color.values()) {
            for(int i = 0; i < BoardExtra.SQUARES_NUM; i++) {
                pieces.put(color, i, new Rook(color, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<Color, Integer, Queen> createAllPossibleMovedQueens() {
        final ImmutableTable.Builder<Color, Integer, Queen> pieces = ImmutableTable.builder();
        for(final Color color : Color.values()) {
            for(int i = 0; i < BoardExtra.SQUARES_NUM; i++) {
                pieces.put(color, i, new Queen(color, i, false));
            }
        }
        return pieces.build();
    }

}
