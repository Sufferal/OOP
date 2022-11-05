package com.chess.logic.player;

import com.chess.logic.GeneralColor;
import com.chess.logic.board.Board;
import com.chess.logic.board.Move;
import com.chess.logic.board.Square;
import com.chess.logic.pieces.Piece;
import com.chess.logic.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlackPlayer extends Player {
    public BlackPlayer(final Board board, final Collection<Move> whiteLegalMoves, final Collection<Move> blackLegalMoves) {
        super(board, blackLegalMoves, whiteLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public GeneralColor getColor() {
        return GeneralColor.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    protected Collection<Move> findKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentsLegals) {
        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            // Black king side castle
            if(!this.board.getSquare(5).isSquareOccupied() &&
                    !this.board.getSquare(6).isSquareOccupied()) {
                final Square rookSquare = this.board.getSquare(7);

                if(rookSquare.isSquareOccupied() && rookSquare.getPiece().isFirstMove()) {
                    if(Player.findAttacksOnSquare(5, opponentsLegals).isEmpty() &&
                            Player.findAttacksOnSquare(6, opponentsLegals).isEmpty() &&
                            rookSquare.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new Move.KingSideCastleMove(this.board, this.playerKing, 6,
                                (Rook)rookSquare.getPiece(), rookSquare.getSquareCoordinate(), 5));
                    }
                }
            }

            // Black queen side castle
            if(!this.board.getSquare(1).isSquareOccupied() &&
                    !this.board.getSquare(2).isSquareOccupied() &&
                    !this.board.getSquare(3).isSquareOccupied()) {

                final Square rookSquare = this.board.getSquare(0);

                if(rookSquare.isSquareOccupied() && rookSquare.getPiece().isFirstMove() &&
                        Player.findAttacksOnSquare(2, opponentsLegals).isEmpty() &&
                        Player.findAttacksOnSquare(3, opponentsLegals).isEmpty() &&
                        rookSquare.getPiece().getPieceType().isRook()) {
                    kingCastles.add(new Move.QueenSideCastleMove(this.board, this.playerKing, 2,
                            (Rook) rookSquare.getPiece(), rookSquare.getSquareCoordinate(), 3));
                }
            }
        }



        return ImmutableList.copyOf(kingCastles);
    }
}
