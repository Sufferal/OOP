package com.chess.logic.board;

import com.chess.logic.board.Board.BoardCreation;
import com.chess.logic.pieces.Pawn;
import com.chess.logic.pieces.Piece;
import com.chess.logic.pieces.Rook;

public abstract class Move {

    protected final Board board;
    protected final int endCoordinate;
    protected final Piece movedPiece;
    protected final boolean isFirstMove;

    private Move(final Board board,
                 final Piece pieceMoved,
                 final int endCoordinate) {
        this.board = board;
        this.endCoordinate = endCoordinate;
        this.movedPiece = pieceMoved;
        this.isFirstMove = pieceMoved.isFirstMove();
    }

    private Move(final Board board,
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
        final BoardCreation boardCreation = new BoardCreation();
        this.board.currentPlayer().getActivePieces().stream().filter(piece -> !this.movedPiece.equals(piece)).forEach(boardCreation::setPiece);
        this.board.currentPlayer().getOpponent().getActivePieces().forEach(boardCreation::setPiece);
        boardCreation.setPiece(this.movedPiece.movePiece(this));
        boardCreation.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
        boardCreation.setMoveShift(this);
        return boardCreation.create();
    }

    public Board undo() {
        final BoardCreation boardCreation = new BoardCreation();
        this.board.getAllPieces().forEach(boardCreation::setPiece);
        boardCreation.setMoveMaker(this.board.currentPlayer().getColor());
        return boardCreation.create();
    }

    String disambiguationFile() {
        for(final Move move : this.board.currentPlayer().getLegalMoves()) {
            if(move.getEndCoordinate() == this.endCoordinate && !this.equals(move) &&
                    this.movedPiece.getPieceType().equals(move.getMovedPiece().getPieceType())) {
                return BoardExtra.INSTANCE.getPosAtCoordinate(this.movedPiece.getPiecePos()).substring(0, 1);
            }
        }
        return "";
    }

    public enum MoveStatus {
        DONE {
            @Override
            public boolean isDone() {
                return true;
            }
        },
        ILLEGAL_MOVE {
            @Override
            public boolean isDone() {
                return false;
            }
        },
        LEAVES_PLAYER_IN_CHECK {
            @Override
            public boolean isDone() {
                return false;
            }
        };

        public abstract boolean isDone();
    }

    public static class PawnPromotion
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
            final BoardCreation boardCreation = new BoardCreation();
            pawnMovedBoard.currentPlayer().getActivePieces().stream().filter(piece -> !this.promotedPawn.equals(piece)).forEach(boardCreation::setPiece);
            pawnMovedBoard.currentPlayer().getOpponent().getActivePieces().forEach(boardCreation::setPiece);
            boardCreation.setPiece(this.promotionPiece.movePiece(this));
            boardCreation.setMoveMaker(pawnMovedBoard.currentPlayer().getColor());
            boardCreation.setMoveShift(this);
            return boardCreation.create();
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

    public static class MajorMove
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

    public static class MajorAttackMove
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

    public static class PawnMove
            extends Move {

        public PawnMove(final Board board,
                        final Piece pieceMoved,
                        final int endCoordinate) {
            super(board, pieceMoved, endCoordinate);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnMove && super.equals(other);
        }

        @Override
        public String toString() {
            return BoardExtra.INSTANCE.getPosAtCoordinate(this.endCoordinate);
        }

    }

    public static class PawnAttackMove
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

    public static class PawnEnPassantAttack extends PawnAttackMove {

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
            final BoardCreation boardCreation = new BoardCreation();
            this.board.currentPlayer().getActivePieces().stream().filter(piece -> !this.movedPiece.equals(piece)).forEach(boardCreation::setPiece);
            this.board.currentPlayer().getOpponent().getActivePieces().stream().filter(piece -> !piece.equals(this.getAttackedPiece())).forEach(boardCreation::setPiece);
            boardCreation.setPiece(this.movedPiece.movePiece(this));
            boardCreation.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
            boardCreation.setMoveShift(this);
            return boardCreation.create();
        }

        @Override
        public Board undo() {
            final BoardCreation boardCreation = new BoardCreation();
            this.board.getAllPieces().forEach(boardCreation::setPiece);
            boardCreation.setEnPassantPawn((Pawn)this.getAttackedPiece());
            boardCreation.setMoveMaker(this.board.currentPlayer().getColor());
            return boardCreation.create();
        }

    }

    public static class PawnJump
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
            final BoardCreation boardCreation = new BoardCreation();
            this.board.currentPlayer().getActivePieces().stream().filter(piece -> !this.movedPiece.equals(piece)).forEach(boardCreation::setPiece);
            this.board.currentPlayer().getOpponent().getActivePieces().forEach(boardCreation::setPiece);
            final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
            boardCreation.setPiece(movedPawn);
            boardCreation.setEnPassantPawn(movedPawn);
            boardCreation.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
            boardCreation.setMoveShift(this);
            return boardCreation.create();
        }

        @Override
        public String toString() {
            return BoardExtra.INSTANCE.getPosAtCoordinate(this.endCoordinate);
        }
    }

    static abstract class CastleMove
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
            final BoardCreation boardCreation = new BoardCreation();
            for (final Piece piece : this.board.getAllPieces()) {
                if (!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
                    boardCreation.setPiece(piece);
                }
            }
            boardCreation.setPiece(this.movedPiece.movePiece(this));
            boardCreation.setPiece(new Rook(this.castleRook.getPieceColor(), this.castleRookEnd, false));
            boardCreation.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
            boardCreation.setMoveShift(this);
            return boardCreation.create();
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

    public static class KingSideCastleMove
            extends CastleMove {

        public KingSideCastleMove(final Board board,
                                  final Piece pieceMoved,
                                  final int endCoordinate,
                                  final Rook castleRook,
                                  final int castleRookStart,
                                  final int castleRookEnd) {
            super(board, pieceMoved, endCoordinate, castleRook, castleRookStart,
                    castleRookEnd);
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof KingSideCastleMove)) {
                return false;
            }
            final KingSideCastleMove otherKingSideCastleMove = (KingSideCastleMove) other;
            return super.equals(otherKingSideCastleMove) && this.castleRook.equals(otherKingSideCastleMove.getCastleRook());
        }

        @Override
        public String toString() {
            return "O-O";
        }
    }

    public static class QueenSideCastleMove
            extends CastleMove {

        public QueenSideCastleMove(final Board board,
                                   final Piece pieceMoved,
                                   final int endCoordinate,
                                   final Rook castleRook,
                                   final int castleRookStart,
                                   final int rookCastleEnd) {
            super(board, pieceMoved, endCoordinate, castleRook, castleRookStart,
                    rookCastleEnd);
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof QueenSideCastleMove)) {
                return false;
            }
            final QueenSideCastleMove otherQueenSideCastleMove = (QueenSideCastleMove) other;
            return super.equals(otherQueenSideCastleMove) && this.castleRook.equals(otherQueenSideCastleMove.getCastleRook());
        }

        @Override
        public String toString() {
            return "O-O-O";
        }
    }

    static abstract class AttackMove
            extends Move {

        private final Piece attackedPiece;

        AttackMove(final Board board,
                   final Piece pieceMoved,
                   final int endCoordinate,
                   final Piece pieceAttacked) {
            super(board, pieceMoved, endCoordinate);
            this.attackedPiece = pieceAttacked;
        }

        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AttackMove)) {
                return false;
            }
            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }

        @Override
        public boolean isAttack() {
            return true;
        }

    }

    private static class InvalidMove
            extends Move {

        private InvalidMove() {
            super(null, -1);
        }

        @Override
        public int getCurrentCoordinate() {
            return -1;
        }

        @Override
        public int getEndCoordinate() {
            return -1;
        }

        @Override
        public Board realize() {
            throw new RuntimeException("You cannot realize invalid move!");
        }

        @Override
        public String toString() {
            return "Invalid Move";
        }
    }

    public static class MoveCreation {

        private static final Move INVALID_MOVE = new InvalidMove();

        private MoveCreation() {
            throw new RuntimeException("You cannot instantiate this class!");
        }

        public static Move getInvalidMove() {
            return INVALID_MOVE;
        }

        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int endCoordinate) {
            for (final Move move : board.getAllLegalMoves()) {
                if (move.getCurrentCoordinate() == currentCoordinate &&
                        move.getEndCoordinate() == endCoordinate) {
                    return move;
                }
            }
            return INVALID_MOVE;
        }
    }
}