package com.chess.logic.move.castle;

import com.chess.logic.board.Board;
import com.chess.logic.pieces.Piece;
import com.chess.logic.pieces.Rook;

public class QueenSideCastleMove
        extends CastleMove {

    public QueenSideCastleMove(final Board board,
                               final Piece pieceMoved,
                               final int endCoordinate,
                               final Rook castleRook,
                               final int castleRookStart,
                               final int rookCastleEnd) {
        super(board, pieceMoved, endCoordinate, castleRook, castleRookStart,
                rookCastleEnd);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof QueenSideCastleMove)) {
            return false;
        }
        final QueenSideCastleMove otherQueenSideCastleMove = (QueenSideCastleMove) other;
        return super.equals(otherQueenSideCastleMove) && this.castleRook.equals(otherQueenSideCastleMove.getCastleRook());
    }

    @Override
    public String toString() {
        return "O-O-O";
    }
}
