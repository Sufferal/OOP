package com.chess.logic.board;

import com.chess.logic.pieces.Pawn;
import com.chess.logic.pieces.Piece;
import com.chess.logic.pieces.Rook;

import static com.chess.logic.board.Board.*;

public abstract class Move {

    protected final Board board;
    protected final Piece currPiece;
    protected final int endCoordinate;
    protected final boolean isFirstMove;

    public static final Move INVALID_MOVE = new InvalidMove();

    private Move(final Board board,
                final Piece currPiece,
                final int endCoordinate) {
        this.board = board;
        this.currPiece = currPiece;
        this.endCoordinate = endCoordinate;
        this.isFirstMove = currPiece.isFirstMove();
    }

    private Move(final Board board,
                 final int endCoordinate) {
        this.board = board;
        this.endCoordinate = endCoordinate;
        this.currPiece = null;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + this.endCoordinate;
        result = prime * result + this.currPiece.hashCode();
        result = prime * result + this.currPiece.getPieceCoordinate();

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
        return getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
                getEndCoordinate() == otherMove.getEndCoordinate() &&
                getCurrPiece().equals(otherMove.getCurrPiece());
    }

    public Board getBoard() { return this.board; }

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

    public static class MajorAttackMove extends AttackMove {

        public MajorAttackMove(Board board, Piece currPiece, int endCoordinate, Piece attackedPiece) {
            super(board, currPiece, endCoordinate, attackedPiece);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MajorAttackMove && super.equals(other);
        }

        @Override
        public String toString() {
            return currPiece.getPieceType() + BoardExtra.getPositionAtCoordinate(this.endCoordinate);
        }
    }

    public static final class MajorMove extends Move {
        public MajorMove(final Board board, final Piece currPiece, final int endCoordinate) {
            super(board, currPiece, endCoordinate);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MajorMove && super.equals(other);
        }

        @Override
        public String toString() {
            return currPiece.getPieceType().toString() + BoardExtra.getPositionAtCoordinate(this.endCoordinate);
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

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof PawnMove && super.equals(other);
        }

        @Override
        public String toString() {
            return BoardExtra.getPositionAtCoordinate(this.endCoordinate);
        }
    }

    public static class PawnAttackMove extends AttackMove {
        public PawnAttackMove(final Board board, final Piece currPiece, final int endCoordinate, final Piece attackedPiece) {
            super(board, currPiece, endCoordinate, attackedPiece);
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof PawnAttackMove && super.equals(other);
        }

        @Override
        public String toString() {
            return BoardExtra.getPositionAtCoordinate(this.currPiece.getPieceCoordinate()).substring(0, 1) + "x" +
                    BoardExtra.getPositionAtCoordinate(this.endCoordinate);
        }
    }

    public static final class PawnEnPassantAttackMove extends PawnAttackMove {
        public PawnEnPassantAttackMove(final Board board, final Piece currPiece, final int endCoordinate, final Piece attackedPiece) {
            super(board, currPiece, endCoordinate, attackedPiece);
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof PawnEnPassantAttackMove && super.equals(other);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for(final Piece piece: this.board.currentPlayer().getActivePieces()) {
                if(!this.currPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }

            for (final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
                if(!piece.equals(this.getAttackedPiece())) {
                    builder.setPiece(piece);
                }
            }

            builder.setPiece(this.currPiece.movePiece(this));
            builder.setMover(this.board.currentPlayer().getOpponent().getColor());

            return builder.build();
        }
    }

    public static class PawnPromotion extends Move {

        final Move specialMove;
        final Pawn promotedPawn;

        public PawnPromotion(final Move specialMove) {
            super(specialMove.getBoard(), specialMove.getCurrPiece(), specialMove.getEndCoordinate());
            this.specialMove = specialMove;
            this.promotedPawn = (Pawn) specialMove.getCurrPiece();
        }

        @Override
        public int hashCode() {
            return specialMove.hashCode() + (31 * specialMove.hashCode());
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof PawnPromotion && (super.equals(other));
        }

        @Override
        public Board execute() {

            final Board pawnMovedBoard = this.specialMove.execute();
            final Board.Builder builder = new Builder();

            for (final Piece piece: pawnMovedBoard.currentPlayer().getActivePieces()) {
                if (!this.promotedPawn.equals(piece)) {
                    builder.setPiece(piece);
                }
            }

            for (final Piece piece: pawnMovedBoard.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            builder.setPiece(this.promotedPawn.getPromotionPiece().movePiece(this));
            builder.setMover(pawnMovedBoard.currentPlayer().getColor());
            return builder.build();
        }

        @Override
        public boolean isAttack() {
            return this.specialMove.isAttack();
        }

        @Override
        public Piece getAttackedPiece() {
            return this.specialMove.getAttackedPiece();
        }

        @Override
        public String toString() {
            return "";
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

        @Override
        public String toString() {
            return BoardExtra.getPositionAtCoordinate(this.endCoordinate);
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

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + this.castledRook.hashCode();
            result = prime * result + this.castleRookEnd;
            return result;
        }

        @Override
        public boolean equals(final Object other) {
            if(this == other) {
                return true;
            }
            if(!(other instanceof CastleMove)) {
                return false;
            }
            final CastleMove otherCastleMove = (CastleMove) other;
            return super.equals(otherCastleMove) && this.castledRook.equals(otherCastleMove.getCastledRook());
        }
    }

    public static final class KingSideCastleMove extends CastleMove {
        public KingSideCastleMove(final Board board, final Piece currPiece, final int endCoordinate,
                                  final Rook castledRook, final int castleRookStart, final int castleRookEnd) {
            super(board, currPiece, endCoordinate, castledRook, castleRookStart, castleRookEnd);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof KingSideCastleMove && super.equals(other);
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
        public boolean equals(final Object other) {
            return this == other || other instanceof QueenSideCastleMove && super.equals(other);
        }

        @Override
        public String toString() {
            return "O-O-O";
        }
    }

    public static final class InvalidMove extends Move {
        public InvalidMove() {
            super(null, 65);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("You cannot execute an invalid move");
        }

        @Override
        public int getCurrentCoordinate() { return -1; }
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
