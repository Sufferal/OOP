package com.chess.logic.move.pawn;

import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.move.Move;
import com.chess.logic.pieces.Pawn;

public class PawnJump
        extends Move {

    public PawnJump(final Board board,
                    final Pawn pieceMoved,
                    final int endCoordinate) {
        super(board, pieceMoved, endCoordinate);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof PawnJump && super.equals(other);
    }

    @Override
    public Board realize() {
        final Board.Builder builder = new Board.Builder();
        this.board.currentPlayer().getActivePieces().stream().filter(piece -> !this.movedPiece.equals(piece)).forEach(builder::setPiece);
        this.board.currentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);
        final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
        builder.setPiece(movedPawn);
        builder.setEnPassantPawn(movedPawn);
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
        builder.setMoveShift(this);
        return builder.build();
    }

    @Override
    public String toString() {
        return BoardExtra.INSTANCE.getPosAtCoordinate(this.endCoordinate);
    }
}
