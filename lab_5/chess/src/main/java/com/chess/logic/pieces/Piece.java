package com.chess.logic.pieces;

import com.chess.logic.GeneralColor;
import com.chess.logic.board.Board;
import com.chess.logic.board.Move;

import java.util.Collection;
import java.util.List;

public abstract class Piece {
    protected final int pieceCoordinate;
    protected final GeneralColor pieceColor;
    protected final boolean isFirstMove;

    public Piece(int pieceCoordinate, final GeneralColor pieceColor) {
        this.pieceCoordinate = pieceCoordinate;
        this.pieceColor = pieceColor;
        this.isFirstMove = false;
    }

    public GeneralColor getPieceColor() {
        return this.pieceColor;
    }

    protected boolean isFirstMove() {
        return this.isFirstMove;
    }

    public abstract Collection<Move> searchLegalMoves(final Board board);

    public int getPieceCoordinate() {
        return this.pieceCoordinate;
    }

    public enum PieceType {
        Pawn("P"),
        Knight("N"),
        Bishop("B"),
        Rook("R"),
        Queen("Q"),
        King("K");

        private final String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }
    }
}