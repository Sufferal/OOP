package com.chess.logic.move.pawn;

import com.chess.logic.board.Board;
import com.chess.logic.pieces.Pawn;
import com.chess.logic.pieces.Piece;

public class PawnEnPassantAttack extends PawnAttackMove {

    public PawnEnPassantAttack(final Board board,
                               final Piece pieceMoved,
                               final int endCoordinate,
                               final Piece pieceAttacked) {
        super(board, pieceMoved, endCoordinate, pieceAttacked);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof PawnEnPassantAttack && super.equals(other);
    }

    @Override
    public Board realize() {
        final Board.Builder builder = new Board.Builder();
        this.board.currentPlayer().getActivePieces().stream().filter(piece -> !this.movedPiece.equals(piece)).forEach(builder::setPiece);
        this.board.currentPlayer().getOpponent().getActivePieces().stream().filter(piece -> !piece.equals(this.getAttackedPiece())).forEach(builder::setPiece);
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
        builder.setMoveShift(this);
        return builder.build();
    }

    @Override
    public Board undo() {
        final Board.Builder builder = new Board.Builder();
        this.board.getAllPieces().forEach(builder::setPiece);
        builder.setEnPassantPawn((Pawn) this.getAttackedPiece());
        builder.setMoveMaker(this.board.currentPlayer().getColor());
        return builder.build();
    }

}
