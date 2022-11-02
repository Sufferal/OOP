package com.chess.logic.board;

import com.chess.logic.pieces.Pawn;
import com.chess.logic.pieces.Piece;
import com.chess.logic.pieces.Rook;

import static com.chess.logic.board.Board.*;

public abstract class Move {

    final Board board;
    final Piece currPiece;
    final int endCoordinate;

    public static final Move INVALID_MOVE = new InvalidMove();

    private Move(final Board board,
                final Piece currPiece,
                final int endCoordinate) {
        this.board = board;
        this.currPiece = currPiece;
        this.endCoordinate = endCoordinate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + this.endCoordinate;
        result = prime * result + this.currPiece.hashCode();

        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if(this == other) {
            return true;
        }
        if(!(other instanceof Move)) {
            return false;
        }
        final Move otherMove = (Move) other;
        return getEndCoordinate() == otherMove.getEndCoordinate() &&
                getCurrPiece().equals(otherMove.getCurrPiece());
    }

    public int getCurrentCoordinate() {
        return this.getCurrPiece().getPieceCoordinate();
    }

    public int getEndCoordinate() {
        return this.endCoordinate;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
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

    public Board execute() {

        final Builder builder = new Builder();

        for (final Piece piece: this.board.currentPlayer().getActivePieces()) {
            if(!this.currPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }

        for(final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        builder.setPiece(this.currPiece.movePiece(this));
        builder.setMover(this.board.currentPlayer().getOpponent().getColor());

        return builder.build();
    }

    public static final class MajorMove extends Move {
        public MajorMove(final Board board, final Piece currPiece, final int endCoordinate) {
            super(board, currPiece, endCoordinate);
        }

    }

    public static class AttackMove extends Move {

        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece currPiece, final int endCoordinate, final Piece attackedPiece) {
            super(board, currPiece, endCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(final Object other) {
            if(this == other) {
                return true;
            }
            if(!(other instanceof AttackMove)) {
                return false;
            }
            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
        }

        @Override
        public Board execute() {
            return null;
        }

        @Override
        public boolean isAttack() {
            return true;
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }
    }

    public static final class PawnMove extends Move {
        public PawnMove(final Board board, final Piece currPiece, final int endCoordinate) {
            super(board, currPiece, endCoordinate);
        }

    }

    public static class PawnAttackMove extends AttackMove {
        public PawnAttackMove(final Board board, final Piece currPiece, final int endCoordinate, final Piece attackedPiece) {
            super(board, currPiece, endCoordinate, attackedPiece);
        }

    }

    public static final class PawnEnPassantAttackMove extends PawnAttackMove {
        public PawnEnPassantAttackMove(final Board board, final Piece currPiece, final int endCoordinate, final Piece attackedPiece) {
            super(board, currPiece, endCoordinate, attackedPiece);
        }

    }

    public static final class PawnJump extends Move {
        public PawnJump(final Board board, final Piece currPiece, final int endCoordinate) {
            super(board, currPiece, endCoordinate);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();

            for (final Piece piece: this.board.currentPlayer().getActivePieces()) {
                if(!this.currPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }

            for (final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            final Pawn movedPawn = (Pawn) this.currPiece.movePiece(this);

            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMover(this.board.currentPlayer().getOpponent().getColor());

            return builder.build();
        }
    }

    static abstract class CastleMove extends Move {
        protected final Rook castledRook;
        protected final int castleRookStart;
        protected final int castleRookEnd;

        public CastleMove(final Board board, final Piece currPiece, final int endCoordinate,
                          final Rook castledRook, final int castleRookStart, final int castleRookEnd) {
            super(board, currPiece, endCoordinate);
            this.castledRook = castledRook;
            this.castleRookStart = castleRookStart;
            this.castleRookEnd = castleRookEnd;
        }

        public Rook getCastledRook() {
            return this.castledRook;
        }

        @Override
        public boolean isCastlingMove() {
            return true;
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();

            for (final Piece piece: this.board.currentPlayer().getActivePieces()) {
                if(!this.currPiece.equals(piece) && !this.castledRook.equals(piece)) {
                    builder.setPiece(piece);
                }
            }

            for (final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            builder.setPiece(this.currPiece.movePiece(this));
            builder.setPiece(new Rook(this.castleRookEnd, this.castledRook.getPieceColor()));
            builder.setMover(this.board.currentPlayer().getOpponent().getColor());

            return builder.build();
        }
    }

    public static final class KingSideCastleMove extends CastleMove {
        public KingSideCastleMove(final Board board, final Piece currPiece, final int endCoordinate,
                                  final Rook castledRook, final int castleRookStart, final int castleRookEnd) {
            super(board, currPiece, endCoordinate, castledRook, castleRookStart, castleRookEnd);
        }

        @Override
        public String toString() {
            return "O-O";
        }
    }

    public static final class QueenSideCastleMove extends CastleMove {
        public QueenSideCastleMove(final Board board, final Piece currPiece, final int endCoordinate,
                                   final Rook castledRook, final int castleRookStart, final int castleRookEnd) {
            super(board, currPiece, endCoordinate, castledRook, castleRookStart, castleRookEnd);
        }

        @Override
        public String toString() {
            return "O-O-O";
        }
    }

    public static final class InvalidMove extends Move {
        public InvalidMove() {
            super(null , null, -1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("You cannot execute an invalid move");
        }
    }

    public static class MoveFactory {
        private MoveFactory() {
            throw new RuntimeException("You cannot instantiate this class");
        }

        public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate) {
            for(final Move move: board.getAllLegalMoves()) {
                if(move.getCurrentCoordinate() == currentCoordinate &&
                 move.getEndCoordinate() == destinationCoordinate) {
                    return move;
                }
            }

            return INVALID_MOVE;
        }
    }
}
