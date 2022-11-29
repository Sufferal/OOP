package com.chess.logic.pieces;

import com.chess.logic.Color;
import com.chess.logic.board.Board;
import com.chess.logic.move.Move;

import java.util.Collection;

public abstract class Piece {

    final PieceType pieceType;
    final Color pieceColor;
    final int piecePos;
    private final boolean isFirstMove;
    private final int cachedHashCode;

    Piece(final PieceType type,
          final Color color,
          final int piecePos,
          final boolean isFirstMove) {
        this.pieceType = type;
        this.piecePos = piecePos;
        this.pieceColor = color;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = computeHashCode();
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }
    public Color getPieceColor() {
        return this.pieceColor;
    }
    public int getPiecePos() {
        return this.piecePos;
    }
    public boolean isFirstMove() {
        return this.isFirstMove;
    }
    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    }

    public abstract int locationBonus();
    public abstract Piece movePiece(Move move);
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return this.piecePos == otherPiece.piecePos && this.pieceType == otherPiece.pieceType &&
                this.pieceColor == otherPiece.pieceColor && this.isFirstMove == otherPiece.isFirstMove;
    }

    @Override
    public int hashCode() {
        return this.cachedHashCode;
    }
    private int computeHashCode() {
        int result = this.pieceType.hashCode();
        result = 31 * result + this.pieceColor.hashCode();
        result = 31 * result + this.piecePos;
        result = 31 * result + (this.isFirstMove ? 1 : 0);
        return result;
    }

    public enum PieceType {

        PAWN(100, "P"),
        KNIGHT(300, "N"),
        BISHOP(350, "B"),
        ROOK(500, "R"),
        QUEEN(900, "Q"),
        KING(9999999, "K");

        private final int value;
        private final String pieceName;

        public int getPieceValue() {
            return this.value;
        }
        @Override
        public String toString() {
            return this.pieceName;
        }

        PieceType(final int val,
                  final String pieceName) {
            this.value = val;
            this.pieceName = pieceName;
        }
    }
}