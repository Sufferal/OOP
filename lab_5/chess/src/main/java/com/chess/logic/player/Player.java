package com.chess.logic.player;

import com.chess.logic.GeneralColor;
import com.chess.logic.board.Board;
import com.chess.logic.board.Move;
import com.chess.logic.pieces.King;
import com.chess.logic.pieces.Piece;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;


    protected Player(final Board board, final Collection<Move> legalMoves, Collection<Move> opponentMoves, boolean isInCheck) {
        this.board = board;
        this.playerKing = initKing();
        this.isInCheck = !Player.findAttacksOnSquare(this.playerKing.getPieceCoordinate(), opponentMoves).isEmpty();
        this.legalMoves = legalMoves;
    }

    public King getPlayerKing() {
        return this.playerKing;
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    private static Collection<Move> findAttacksOnSquare(int pieceCoordinate, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();

        for (final Move move : moves) {
            if(pieceCoordinate == move.getEndCoordinate()) {
                attackMoves.add(move);
            }
        }

        return ImmutableList.copyOf(attackMoves);
    }

    private King initKing() {
        for(final Piece piece: getActivePieces()) {
            if(piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("No king on the board");
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public boolean isInCheckmate() {
        return this.isInCheck && !hasEscapeMoves();
    }

    public boolean isInStalemate() {
        return !this.isInCheck && !hasEscapeMoves();
    }

    protected boolean hasEscapeMoves() {
        for(final Move move: this.legalMoves) {
            final MoveTransition transition = makeMove(move);
            if(transition.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }

    public boolean isCastled() {
        return false;
    }

    public MoveTransition makeMove(final Move move) {

        if(!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard = move.execute();
        final Collection<Move> kingAttacks = Player.findAttacksOnSquare(transitionBoard.currentPlayer()
                .getOpponent().getPlayerKing().getPieceCoordinate(), transitionBoard.currentPlayer().getLegalMoves());

        if(!kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }

        return new MoveTransition(this.board, move, MoveStatus.DONE);
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract GeneralColor getColor();
    public abstract Player getOpponent();
}
