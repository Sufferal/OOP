package com.chess.logic.move.pawn;

import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.move.Move;
import com.chess.logic.pieces.Pawn;
import com.chess.logic.pieces.Piece;

public class PawnPromotion
        extends PawnMove {

    final Move decoratedMove;
    final Pawn promotedPawn;
    final Piece promotionPiece;

    public PawnPromotion(final Move decoratedMove,
                         final Piece promotionPiece) {
        super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getEndCoordinate());
        this.decoratedMove = decoratedMove;
        this.promotedPawn = (Pawn) decoratedMove.getMovedPiece();
        this.promotionPiece = promotionPiece;
    }

    @Override
    public int hashCode() {
        return decoratedMove.hashCode() + (31 * promotedPawn.hashCode());
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof PawnPromotion && (super.equals(other));
    }

    @Override
    public Board realize() {
        final Board pawnMovedBoard = this.decoratedMove.realize();
        final Board.Builder builder = new Board.Builder();
        pawnMovedBoard.currentPlayer().getActivePieces().stream().filter(piece -> !this.promotedPawn.equals(piece)).forEach(builder::setPiece);
        pawnMovedBoard.currentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);
        builder.setPiece(this.promotionPiece.movePiece(this));
        builder.setMoveMaker(pawnMovedBoard.currentPlayer().getColor());
        builder.setMoveShift(this);
        return builder.build();
    }

    @Override
    public boolean isAttack() {
        return this.decoratedMove.isAttack();
    }

    @Override
    public Piece getAttackedPiece() {
        return this.decoratedMove.getAttackedPiece();
    }

    @Override
    public String toString() {
        return BoardExtra.INSTANCE.getPosAtCoordinate(this.movedPiece.getPiecePos()) + "-" +
                BoardExtra.INSTANCE.getPosAtCoordinate(this.endCoordinate) + "=" + this.promotionPiece.getPieceType();
    }

}
