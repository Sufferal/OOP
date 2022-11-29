package com.chess.logic.move.pawn;

import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.move.AttackMove;
import com.chess.logic.pieces.Piece;

public class PawnAttackMove
        extends AttackMove {

    public PawnAttackMove(final Board board,
                          final Piece pieceMoved,
                          final int endCoordinate,
                          final Piece pieceAttacked) {
        super(board, pieceMoved, endCoordinate, pieceAttacked);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof PawnAttackMove && super.equals(other);
    }

    @Override
    public String toString() {
        return BoardExtra.INSTANCE.getPosAtCoordinate(this.movedPiece.getPiecePos()).substring(0, 1) + "x" +
                BoardExtra.INSTANCE.getPosAtCoordinate(this.endCoordinate);
    }

}
