package com.chess.logic.player;

import com.chess.logic.Color;
import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.move.Move;
import com.chess.logic.move.castle.KingSideCastleMove;
import com.chess.logic.move.castle.QueenSideCastleMove;
import com.chess.logic.pieces.Piece;
import com.chess.logic.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.chess.logic.pieces.Piece.PieceType.ROOK;

public final class BlackPlayer extends Player {

    public BlackPlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegalMoves,
                                                    final Collection<Move> opponentLegalMoves) {

        if (!hasCastleOpportunities()) {
            return Collections.emptyList();
        }

        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && this.playerKing.getPiecePos() == 4 && !this.isInCheck) {
            //blacks king side castle
            if (this.board.getPiece(5) == null && this.board.getPiece(6) == null) {
                final Piece kingSideRook = this.board.getPiece(7);
                if (kingSideRook != null && kingSideRook.isFirstMove() &&
                        Player.calculateAttacksOnTile(5, opponentLegalMoves).isEmpty() &&
                        Player.calculateAttacksOnTile(6, opponentLegalMoves).isEmpty() &&
                        kingSideRook.getPieceType() == ROOK) {
                    if (!BoardExtra.isKingPawnTrap(this.board, this.playerKing, 12)) {
                        kingCastles.add(
                                new KingSideCastleMove(this.board, this.playerKing, 6, (Rook) kingSideRook, kingSideRook.getPiecePos(), 5));

                    }
                }
            }
            //blacks queen side castle
            if (this.board.getPiece(1) == null && this.board.getPiece(2) == null &&
                    this.board.getPiece(3) == null) {
                final Piece queenSideRook = this.board.getPiece(0);
                if (queenSideRook != null && queenSideRook.isFirstMove() &&
                        Player.calculateAttacksOnTile(2, opponentLegalMoves).isEmpty() &&
                        Player.calculateAttacksOnTile(3, opponentLegalMoves).isEmpty() &&
                        queenSideRook.getPieceType() == ROOK) {
                    if (!BoardExtra.isKingPawnTrap(this.board, this.playerKing, 12)) {
                        kingCastles.add(
                                new QueenSideCastleMove(this.board, this.playerKing, 2, (Rook) queenSideRook, queenSideRook.getPiecePos(), 3));
                    }
                }
            }
        }
        return Collections.unmodifiableList(kingCastles);
    }

    @Override
    public WhitePlayer getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public String toString() {
        return Color.BLACK.toString();
    }

}
