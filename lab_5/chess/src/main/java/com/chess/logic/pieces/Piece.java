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

    public abstract Collection<Move> searchLegalMoves(final Board board);

    protected boolean isFirstMove() {
        return this.isFirstMove;
    }
}