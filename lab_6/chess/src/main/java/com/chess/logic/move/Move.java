package com.chess.logic.move;

import com.chess.logic.board.Board;
import com.chess.logic.board.Board.Builder;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.pieces.Piece;

public abstract class Move {

    protected final Board board;
    protected final int endCoordinate;
    protected final Piece movedPiece;
    protected final boolean isFirstMove;

    protected Move(final Board board,
                   final Piece pieceMoved,
                   final int endCoordinate) {
        this.board = board;
        this.endCoordinate = endCoordinate;
        this.movedPiece = pieceMoved;
        this.isFirstMove = pieceMoved.isFirstMove();
    }

    protected Move(final Board board,
                 final int endCoordinate) {
        this.board = board;
        this.endCoordinate = endCoordinate;
        this.movedPiece = null;
        this.isFirstMove = false;
    }


    public Board getBoard() {
        return this.board;
    }
    public int getCurrentCoordinate() {
        return this.movedPiece.getPiecePos();
    }
    public int getEndCoordinate() {
        return this.endCoordinate;
    }
    public Piece getMovedPiece() {
        return this.movedPiece;
    }
    public boolean isAttack() {
        return false;
    }
    public boolean isCastlingMove() {
        return false;
    }
    public Piece getAttackedPiece() {
        return null;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + this.endCoordinate;
        result = 31 * result + this.movedPiece.hashCode();
        result = 31 * result + this.movedPiece.getPiecePos();
        result = result + (isFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Move)) {
            return false;
        }
        final Move otherMove = (Move) other;
        return getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
                getEndCoordinate() == otherMove.getEndCoordinate() &&
                getMovedPiece().equals(otherMove.getMovedPiece());
    }

    public Board realize() {
        final Builder builder = new Builder();
        this.board.currentPlayer().getActivePieces().stream().filter(piece -> !this.movedPiece.equals(piece)).forEach(builder::setPiece);
        this.board.currentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
        builder.setMoveShift(this);
        return builder.build();
    }
    public Board undo() {
        final Builder builder = new Builder();
        this.board.getAllPieces().forEach(builder::setPiece);
        builder.setMoveMaker(this.board.currentPlayer().getColor());
        return builder.build();
    }
    protected String disambiguationFile() {
        for(final Move move : this.board.currentPlayer().getLegalMoves()) {
            if(move.getEndCoordinate() == this.endCoordinate && !this.equals(move) &&
                    this.movedPiece.getPieceType().equals(move.getMovedPiece().getPieceType())) {
                return BoardExtra.INSTANCE.getPosAtCoordinate(this.movedPiece.getPiecePos()).substring(0, 1);
            }
        }
        return "";
    }
}