package com.chess.logic.board;

import com.chess.logic.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Square {
    protected final int squareCoordinate;

    private static final Map<Integer, EmptySquare> EMPTY_SQUARES = createAllEmptySquares();

    private static Map<Integer,EmptySquare> createAllEmptySquares() {
        final Map<Integer, EmptySquare> emptySquareMap = new HashMap<>();

        for(int i = 0; i < BoardExtra.NUM_SQUARES; i++) {
            emptySquareMap.put(i, new EmptySquare(i));
        }

        return ImmutableMap.copyOf(emptySquareMap);
    }

    public static Square createSquare(final int squareCoordinate, Piece piece) {
        return piece != null ? new OccupiedSquare(squareCoordinate, piece) : EMPTY_SQUARES.get(squareCoordinate);
    }

    private Square(final int squareCoordinate) {
        this.squareCoordinate = squareCoordinate;
    }

    public abstract boolean isSquareOccupied();
    public abstract Piece getPiece();

    // Empty Square
    public static final class EmptySquare extends Square {
        private EmptySquare(final int coordinate) {
            super(coordinate);
        }

        @java.lang.Override
        public boolean isSquareOccupied() {
            return false;
        }

        @java.lang.Override
        public Piece getPiece() {
            return null;
        }
    }

    // Occupied Square
    public static final class OccupiedSquare extends Square {
        private final Piece pieceSquare;

        private OccupiedSquare(int squareCoordinate, final Piece pieceSquare) {
            super(squareCoordinate);
            this.pieceSquare = pieceSquare;
        }

        @java.lang.Override
        public boolean isSquareOccupied() {
            return true;
        }

        @java.lang.Override
        public Piece getPiece() {
            return this.pieceSquare;
        }
    }
}