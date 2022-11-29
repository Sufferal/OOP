package com.chess.logic.move.pawn;

import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.move.Move;
import com.chess.logic.pieces.Piece;

public class PawnMove
        extends Move {

    public PawnMove(final Board board,
                    final Piece pieceMoved,
                    final int endCoordinate) {
        super(board, pieceMoved, endCoordinate);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof PawnMove && super.equals(other);
    }

    @Override
    public String toString() {
        return BoardExtra.INSTANCE.getPosAtCoordinate(this.endCoordinate);
    }

}
