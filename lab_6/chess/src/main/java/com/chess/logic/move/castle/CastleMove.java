package com.chess.logic.move.castle;

import com.chess.logic.board.Board;
import com.chess.logic.move.Move;
import com.chess.logic.pieces.Piece;
import com.chess.logic.pieces.Rook;

abstract class CastleMove
        extends Move {

    final Rook castleRook;
    final int castleRookStart;
    final int castleRookEnd;

    CastleMove(final Board board,
               final Piece pieceMoved,
               final int endCoordinate,
               final Rook castleRook,
               final int castleRookStart,
               final int castleRookEnd) {
        super(board, pieceMoved, endCoordinate);
        this.castleRook = castleRook;
        this.castleRookStart = castleRookStart;
        this.castleRookEnd = castleRookEnd;
    }

    Rook getCastleRook() {
        return this.castleRook;
    }

    @Override
    public boolean isCastlingMove() {
        return true;
    }

    @Override
    public Board realize() {
        final Board.Builder builder = new Board.Builder();
        for (final Piece piece : this.board.getAllPieces()) {
            if (!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setPiece(new Rook(this.castleRook.getPieceColor(), this.castleRookEnd, false));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
        builder.setMoveShift(this);
        return builder.build();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + this.castleRook.hashCode();
        result = prime * result + this.castleRookEnd;
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CastleMove)) {
            return false;
        }
        final CastleMove otherCastleMove = (CastleMove) other;
        return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
    }

}
