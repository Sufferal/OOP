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

public final class WhitePlayer extends Player {

    public WhitePlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegalMoves,
                                                    final Collection<Move> opponentLegalMoves) {

        if(!hasCastleOpportunities()) {
            return Collections.emptyList();
        }

        final List<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && this.playerKing.getPiecePos() == 60 && !this.isInCheck()) {
            //whites king side castle
            if(this.board.getPiece(61) == null && this.board.getPiece(62) == null) {
                final Piece kingSideRook = this.board.getPiece(63);
                if(kingSideRook != null && kingSideRook.isFirstMove()) {
                    if(Player.calculateAttacksOnTile(61, opponentLegalMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(62, opponentLegalMoves).isEmpty() &&
                            kingSideRook.getPieceType() == ROOK) {
                        if(!BoardExtra.isKingPawnTrap(this.board, this.playerKing, 52)) {
                            kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62, (Rook) kingSideRook, kingSideRook.getPiecePos(), 61));
                        }
                    }
                }
            }
            //whites queen side castle
            if(this.board.getPiece(59) == null && this.board.getPiece(58) == null &&
                    this.board.getPiece(57) == null) {
                final Piece queenSideRook = this.board.getPiece(56);
                if(queenSideRook != null && queenSideRook.isFirstMove()) {
                    if(Player.calculateAttacksOnTile(58, opponentLegalMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(59, opponentLegalMoves).isEmpty() && queenSideRook.getPieceType() == ROOK) {
                        if(!BoardExtra.isKingPawnTrap(this.board, this.playerKing, 52)) {
                            kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58, (Rook) queenSideRook, queenSideRook.getPiecePos(), 59));
                        }
                    }
                }
            }
        }
        return Collections.unmodifiableList(kingCastles);
    }

    @Override
    public BlackPlayer getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    @Override
    public String toString() {
        return Color.WHITE.toString();
    }

}
