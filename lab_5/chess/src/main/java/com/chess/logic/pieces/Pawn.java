package com.chess.logic.pieces;

import com.chess.logic.GeneralColor;
import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.board.Move;
import com.google.common.collect.ImmutableList;

import static com.chess.logic.board.Move.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece{
    private final static int[] CANDIDATE_MOVES = { 7, 8, 9, 16 };

    public Pawn(final int pieceCoordinate, final GeneralColor pieceColor) {
        super(PieceType.Pawn, pieceCoordinate, pieceColor, true);
    }

    public Pawn(final int pieceCoordinate, final GeneralColor pieceColor, final boolean isFirstMove) {
        super(PieceType.Pawn, pieceCoordinate, pieceColor, isFirstMove);
    }

    @Override
    public Collection<Move> searchLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateOffset: CANDIDATE_MOVES) {
            final int endCoordinate = this.pieceCoordinate + (this.pieceColor.getDirection() * candidateOffset);

            if(!BoardExtra.isValidSquareCoordinate(endCoordinate)) {
                continue;
            }

            if (candidateOffset == 8 && !board.getSquare(endCoordinate).isSquareOccupied()) {

                if (this.pieceColor.isPawnPromotionSquare(endCoordinate)) {
                    legalMoves.add(new PawnPromotion(new PawnMove(board, this, endCoordinate)));
                } else {
                    legalMoves.add(new PawnMove(board, this, endCoordinate));
                }
            } else if (candidateOffset == 16 && this.isFirstMove() &&
                    ((BoardExtra.SECOND_ROW[this.pieceCoordinate] && this.getPieceColor().isBlack()) ||
                    (BoardExtra.SEVENTH_ROW[this.pieceCoordinate] && this.getPieceColor().isWhite()))
            ) {
                final int behindEndCoordinate = this.pieceCoordinate + (this.pieceColor.getDirection() * 8);
                if(!board.getSquare(behindEndCoordinate).isSquareOccupied() &&
                        !board.getSquare(endCoordinate).isSquareOccupied()) {
                    legalMoves.add(new PawnJump(board, this, endCoordinate));
                }
            } else if (candidateOffset == 7 &&
                            !((BoardExtra.EIGHTH_FILE[this.pieceCoordinate] && this.pieceColor.isWhite()) ||
                            (BoardExtra.FIRST_FILE[this.pieceCoordinate] && this.pieceColor.isBlack())))
                {
                if(board.getSquare(endCoordinate).isSquareOccupied()) {
                    final Piece endPiece = board.getSquare(endCoordinate).getPiece();
                    if (this.pieceColor != endPiece.getPieceColor()) {
                        if (this.pieceColor.isPawnPromotionSquare(endCoordinate)) {
                            legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, endCoordinate, endPiece)));
                        } else {
                            legalMoves.add(new PawnAttackMove(board, this, endCoordinate, endPiece));
                        }
                    }
                } else if (board.getEnPassantPawn() != null) {
                    if(board.getEnPassantPawn().getPieceCoordinate() == (this.pieceCoordinate
                            + (this.pieceColor.getOppositeDirection()))) {
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if(this.pieceColor != pieceOnCandidate.getPieceColor()) {
                            legalMoves.add(new PawnEnPassantAttackMove(board, this, endCoordinate,
                                    pieceOnCandidate));
                        }
                    }
                }
            } else if (candidateOffset == 9 &&
                            !((BoardExtra.EIGHTH_FILE[this.pieceCoordinate] && this.pieceColor.isBlack()) ||
                            (BoardExtra.FIRST_FILE[this.pieceCoordinate] && this.pieceColor.isWhite())))
            {
                if(board.getSquare(endCoordinate).isSquareOccupied()) {
                    final Piece endPiece = board.getSquare(endCoordinate).getPiece();
                    if (this.pieceColor != endPiece.getPieceColor()) {
                        if (this.pieceColor.isPawnPromotionSquare(endCoordinate)) {
                            legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, endCoordinate, endPiece)));
                        } else {
                            legalMoves.add(new PawnAttackMove(board, this, endCoordinate, endPiece));
                        }
                    }
                } else if (board.getEnPassantPawn() != null) {
                    if(board.getEnPassantPawn().getPieceCoordinate() == (this.pieceCoordinate
                            - (this.pieceColor.getOppositeDirection()))) {
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if(this.pieceColor != pieceOnCandidate.getPieceColor()) {
                            legalMoves.add(new PawnEnPassantAttackMove(board, this, endCoordinate,
                                    pieceOnCandidate));
                        }
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getEndCoordinate(), move.getCurrPiece().getPieceColor());
    }

    @Override
    public String toString() {
        return PieceType.Pawn.toString();
    }

    public Piece getPromotionPiece() {
        return new Queen(this.pieceCoordinate, this.pieceColor, false);
    }
}

















