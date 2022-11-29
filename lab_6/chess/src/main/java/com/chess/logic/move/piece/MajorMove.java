package com.chess.logic.move.piece;

import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.move.Move;
import com.chess.logic.pieces.Piece;

public class MajorMove
        extends Move {

    public MajorMove(final Board board,
                     final Piece pieceMoved,
                     final int endCoordinate) {
        super(board, pieceMoved, endCoordinate);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof MajorMove && super.equals(other);
    }

    @Override
    public String toString() {
        return movedPiece.getPieceType().toString() + disambiguationFile() +
                BoardExtra.INSTANCE.getPosAtCoordinate(this.endCoordinate);
    }

}
