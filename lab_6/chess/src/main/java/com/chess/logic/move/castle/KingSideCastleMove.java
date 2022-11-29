package com.chess.logic.move.castle;

import com.chess.logic.board.Board;
import com.chess.logic.pieces.Piece;
import com.chess.logic.pieces.Rook;

public class KingSideCastleMove
        extends CastleMove {

    public KingSideCastleMove(final Board board,
                              final Piece pieceMoved,
                              final int endCoordinate,
                              final Rook castleRook,
                              final int castleRookStart,
                              final int castleRookEnd) {
        super(board, pieceMoved, endCoordinate, castleRook, castleRookStart,
                castleRookEnd);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KingSideCastleMove)) {
            return false;
        }
        final KingSideCastleMove otherKingSideCastleMove = (KingSideCastleMove) other;
        return super.equals(otherKingSideCastleMove) && this.castleRook.equals(otherKingSideCastleMove.getCastleRook());
    }

    @Override
    public String toString() {
        return "O-O";
    }
}
