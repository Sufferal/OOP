package com.chess.logic.board;

import com.chess.logic.pieces.Piece;

public abstract class Move {

    final Board board;
    final Piece currPiece;
    final int endCoordinate;

    private Move(final Board board,
                final Piece currPiece,
                final int endCoordinate) {
        this.board = board;
        this.currPiece = currPiece;
        this.endCoordinate = endCoordinate;
    }

    public int getEndCoordinate() {
        return this.endCoordinate;
    }

    public abstract Board execute();

    public static final class MajorMove extends Move {
        public MajorMove(final Board board, final Piece currPiece, final int endCoordinate) {
            super(board, currPiece, endCoordinate);
        }

        @Override
        public Board execute() {
            return null;
        }
    }

    public static final class AttackMove extends Move {

        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece currPiece, final int endCoordinate, final Piece attackedPiece) {
            super(board, currPiece, endCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }
}
