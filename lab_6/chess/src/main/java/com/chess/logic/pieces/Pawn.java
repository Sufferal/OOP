package com.chess.logic.pieces;

import com.chess.logic.Color;
import com.chess.logic.board.*;
import com.chess.logic.move.*;
import com.chess.logic.move.pawn.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class Pawn
        extends Piece {

    private final static int[] MOVE_COORDINATES = { 8, 16, 7, 9 };

    public Pawn(final Color pieceColor,
                final int piecePos) {
        super(PieceType.PAWN, pieceColor, piecePos, true);
    }

    public Pawn(final Color pieceColor,
                final int piecePosition,
                final boolean isFirstMove) {
        super(PieceType.PAWN, pieceColor, piecePosition, isFirstMove);
    }

    @Override
    public int locationBonus() {
        return this.pieceColor.pawnBonus(this.piecePos);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentOffset : MOVE_COORDINATES) {
            int endCanditate =
                    this.piecePos + (this.pieceColor.getDirection() * currentOffset);
            if (!BoardExtra.isValidSquareCoordinate(endCanditate)) {
                continue;
            }
            if (currentOffset == 8 && board.getPiece(endCanditate) == null) {
                if (this.pieceColor.isPawnPromotionSquare(endCanditate)) {
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, endCanditate), PieceExtra.INSTANCE.getMovedQueen(this.pieceColor, endCanditate)));
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, endCanditate), PieceExtra.INSTANCE.getMovedRook(this.pieceColor, endCanditate)));
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, endCanditate), PieceExtra.INSTANCE.getMovedBishop(this.pieceColor, endCanditate)));
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, endCanditate), PieceExtra.INSTANCE.getMovedKnight(this.pieceColor, endCanditate)));
                }
                else {
                    legalMoves.add(new PawnMove(board, this, endCanditate));
                }
            }
            else if (currentOffset == 16 && this.isFirstMove() &&
                    ((BoardExtra.INSTANCE.RANK_7.get(this.piecePos) && this.pieceColor.isBlack()) ||
                            (BoardExtra.INSTANCE.RANK_2.get(this.piecePos) && this.pieceColor.isWhite()))) {
                final int behindCandidateDestinationCoordinate =
                        this.piecePos + (this.pieceColor.getDirection() * 8);
                if (board.getPiece(endCanditate) == null &&
                        board.getPiece(behindCandidateDestinationCoordinate) == null) {
                    legalMoves.add(new PawnJump(board, this, endCanditate));
                }
            }
            else if (currentOffset == 7 &&
                    !((BoardExtra.INSTANCE.FILE_H.get(this.piecePos) && this.pieceColor.isWhite()) ||
                            (BoardExtra.INSTANCE.FILE_A.get(this.piecePos) && this.pieceColor.isBlack()))) {
                if(board.getPiece(endCanditate) != null) {
                    final Piece pieceOnCandidate = board.getPiece(endCanditate);
                    if (this.pieceColor != pieceOnCandidate.getPieceColor()) {
                        if (this.pieceColor.isPawnPromotionSquare(endCanditate)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, endCanditate, pieceOnCandidate), PieceExtra.INSTANCE.getMovedQueen(this.pieceColor, endCanditate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, endCanditate, pieceOnCandidate), PieceExtra.INSTANCE.getMovedRook(this.pieceColor, endCanditate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, endCanditate, pieceOnCandidate), PieceExtra.INSTANCE.getMovedBishop(this.pieceColor, endCanditate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, endCanditate, pieceOnCandidate), PieceExtra.INSTANCE.getMovedKnight(this.pieceColor, endCanditate)));
                        }
                        else {
                            legalMoves.add(
                                    new PawnAttackMove(board, this, endCanditate, pieceOnCandidate));
                        }
                    }
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePos() ==
                        (this.piecePos + (this.pieceColor.getOppositeDirection()))) {
                    final Piece pieceOnCandidate = board.getEnPassantPawn();
                    if (this.pieceColor != pieceOnCandidate.getPieceColor()) {
                        legalMoves.add(
                                new PawnEnPassantAttack(board, this, endCanditate, pieceOnCandidate));

                    }
                }
            }
            else if (currentOffset == 9 &&
                    !((BoardExtra.INSTANCE.FILE_A.get(this.piecePos) && this.pieceColor.isWhite()) ||
                            (BoardExtra.INSTANCE.FILE_H.get(this.piecePos) && this.pieceColor.isBlack()))) {
                if(board.getPiece(endCanditate) != null) {
                    if (this.pieceColor !=
                            board.getPiece(endCanditate).getPieceColor()) {
                        if (this.pieceColor.isPawnPromotionSquare(endCanditate)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, endCanditate,
                                            board.getPiece(endCanditate)), PieceExtra.INSTANCE.getMovedQueen(this.pieceColor, endCanditate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, endCanditate,
                                            board.getPiece(endCanditate)), PieceExtra.INSTANCE.getMovedRook(this.pieceColor, endCanditate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, endCanditate,
                                            board.getPiece(endCanditate)), PieceExtra.INSTANCE.getMovedBishop(this.pieceColor, endCanditate)));
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this, endCanditate,
                                            board.getPiece(endCanditate)), PieceExtra.INSTANCE.getMovedKnight(this.pieceColor, endCanditate)));
                        }
                        else {
                            legalMoves.add(
                                    new PawnAttackMove(board, this, endCanditate,
                                            board.getPiece(endCanditate)));
                        }
                    }
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePos() ==
                        (this.piecePos - (this.pieceColor.getOppositeDirection()))) {
                    final Piece pieceOnCandidate = board.getEnPassantPawn();
                    if (this.pieceColor != pieceOnCandidate.getPieceColor()) {
                        legalMoves.add(
                                new PawnEnPassantAttack(board, this, endCanditate, pieceOnCandidate));

                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public String toString() {
        return "\u2659";
    }

    @Override
    public Pawn movePiece(final Move move) {
        return PieceExtra.INSTANCE.getMovedPawn(move.getMovedPiece().getPieceColor(), move.getEndCoordinate());
    }

}