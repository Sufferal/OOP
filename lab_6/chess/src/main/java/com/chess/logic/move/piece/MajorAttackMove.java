package com.chess.logic.move.piece;

import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.move.AttackMove;
import com.chess.logic.pieces.Piece;

public class MajorAttackMove
        extends AttackMove {

    public MajorAttackMove(final Board board,
                           final Piece pieceMoved,
                           final int endCoordinate,
                           final Piece pieceAttacked) {
        super(board, pieceMoved, endCoordinate, pieceAttacked);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof MajorAttackMove && super.equals(other);

    }

    @Override
    public String toString() {
        return movedPiece.getPieceType() + disambiguationFile() + "x" +
                BoardExtra.INSTANCE.getPosAtCoordinate(this.endCoordinate);
    }

}
