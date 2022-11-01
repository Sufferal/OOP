package com.chess.logic.pieces;

import com.chess.logic.GeneralColor;
import com.chess.logic.board.Board;
import com.chess.logic.board.Move;

import java.util.Collection;

public abstract class Piece {
    protected final PieceType pieceType;
    protected final int pieceCoordinate;
    protected final GeneralColor pieceColor;
    protected final boolean isFirstMove;

    public Piece(final PieceType pieceType, final int pieceCoordinate, final GeneralColor pieceColor) {
        this.pieceType = pieceType;
        this.pieceCoordinate = pieceCoordinate;
        this.pieceColor = pieceColor;
        this.isFirstMove = false;
    }

    public GeneralColor getPieceColor() { return this.pieceColor; }
    public boolean isFirstMove() {
        return this.isFirstMove;
    }
    public int getPieceCoordinate() {
        return this.pieceCoordinate;
    }
    public PieceType getPieceType() { return this.pieceType; }

    public abstract Collection<Move> searchLegalMoves(final Board board);

    public enum PieceType {
        Pawn("P") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        Knight("N") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        Bishop("B") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        Rook("R") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        Queen("Q") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        King("K") {
            @Override
            public boolean isKing() {
                return true;
            }
        };

        private final String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public abstract boolean isKing();
    }
}