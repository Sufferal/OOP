package com.chess.logic.player;

import com.chess.logic.GeneralColor;
import com.chess.logic.board.Board;
import com.chess.logic.board.Move;
import com.chess.logic.board.Square;
import com.chess.logic.pieces.Piece;
import com.chess.logic.pieces.Rook;
import com.google.common.collect.ImmutableList;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WhitePlayer extends Player {
    public WhitePlayer(final Board board, final Collection<Move> whiteLegalMoves, final Collection<Move> blackLegalMoves) {
        super(board, whiteLegalMoves, blackLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public GeneralColor getColor() {
        return GeneralColor.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> findKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentsLegals) {

        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            // White king side castle
            if(!this.board.getSquare(61).isSquareOccupied() &&
                    !this.board.getSquare(62).isSquareOccupied()) {
                final Square rookSquare = this.board.getSquare(63);

                if(rookSquare.isSquareOccupied() && rookSquare.getPiece().isFirstMove()) {
                    if(Player.findAttacksOnSquare(61, opponentsLegals).isEmpty() &&
                            Player.findAttacksOnSquare(62, opponentsLegals).isEmpty() &&
                    rookSquare.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new Move.KingSideCastleMove(this.board, this.playerKing, 62,
                                (Rook)rookSquare.getPiece(),  rookSquare.getSquareCoordinate(), 61));
                    }
                }
            }

            // White queen side castle
            if(!this.board.getSquare(57).isSquareOccupied() &&
                    !this.board.getSquare(58).isSquareOccupied() &&
                    !this.board.getSquare(59).isSquareOccupied()) {

                final Square rookSquare = this.board.getSquare(56);

                if(rookSquare.isSquareOccupied() && rookSquare.getPiece().isFirstMove() &&
                        Player.findAttacksOnSquare(58, opponentsLegals).isEmpty() &&
                        Player.findAttacksOnSquare(59, opponentsLegals).isEmpty() &&
                        rookSquare.getPiece().getPieceType().isRook()) {
                    kingCastles.add(new Move.QueenSideCastleMove(this.board, this.playerKing, 58,
                            (Rook)rookSquare.getPiece(), rookSquare.getSquareCoordinate(), 59));
                }
            }
        }


        return ImmutableList.copyOf(kingCastles);
    }
}
