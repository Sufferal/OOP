package com.chess.logic.pieces;

import com.chess.logic.Color;
import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.board.Move;
import com.chess.logic.board.Move.MajorAttackMove;
import com.chess.logic.board.Move.MajorMove;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class Queen extends Piece {

    private final static int[] MOVE_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 };

    public Queen(final Color color, final int piecePos) {
        super(PieceType.QUEEN, color, piecePos, true);
    }

    public Queen(final Color color,
                 final int piecePos,
                 final boolean isFirstMove) {
        super(PieceType.QUEEN, color, piecePos, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentOffset : MOVE_COORDINATES) {
            int endCandidate = this.piecePos;
            while (true) {
                if (isFirstFileRemoval(currentOffset, endCandidate) ||
                        isEighthFileRemoval(currentOffset, endCandidate)) {
                    break;
                }
                endCandidate += currentOffset;
                if (!BoardExtra.isValidSquareCoordinate(endCandidate)) {
                    break;
                } else {
                    final Piece pieceAtDestination = board.getPiece(endCandidate);
                    if (pieceAtDestination == null) {
                        legalMoves.add(new MajorMove(board, this, endCandidate));
                    } else {
                        final Color pieceAtDestinationColor = pieceAtDestination.getPieceColor();
                        if (this.pieceColor != pieceAtDestinationColor) {
                            legalMoves.add(new MajorAttackMove(board, this, endCandidate,
                                    pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public int locationBonus() {
        return this.pieceColor.queenBonus(this.piecePos);
    }

    @Override
    public Queen movePiece(final Move move) {
        return PieceExtra.INSTANCE.getMovedQueen(move.getMovedPiece().getPieceColor(), move.getEndCoordinate());
    }

    @Override
    public String toString() {
        return "\u2655";
    }

    private static boolean isFirstFileRemoval(final int currentPosition,
                                              final int candidatePosition) {
        return BoardExtra.INSTANCE.FILE_A.get(candidatePosition) && ((currentPosition == -9)
                || (currentPosition == -1) || (currentPosition == 7));
    }

    private static boolean isEighthFileRemoval(final int currentPosition,
                                               final int candidatePosition) {
        return BoardExtra.INSTANCE.FILE_H.get(candidatePosition) && ((currentPosition == -7)
                || (currentPosition == 1) || (currentPosition == 9));
    }

}