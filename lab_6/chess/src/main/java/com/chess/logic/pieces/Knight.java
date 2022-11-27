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

public final class Knight extends Piece {

    private final static int[] MOVE_COORDINATES = { -17, -15, -10, -6, 6, 10, 15, 17 };

    public Knight(final Color color,
                  final int piecePos) {
        super(PieceType.KNIGHT, color, piecePos, true);
    }

    public Knight(final Color color,
                  final int piecePos,
                  final boolean isFirstMove) {
        super(PieceType.KNIGHT, color, piecePos, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentOffset : MOVE_COORDINATES) {
            if(isFirstFileRemoval(this.piecePos, currentOffset) ||
                    isSecondFileRemoval(this.piecePos, currentOffset) ||
                    isSeventhFileRemoval(this.piecePos, currentOffset) ||
                    isEighthFileRemoval(this.piecePos, currentOffset)) {
                continue;
            }
            final int endCandidate = this.piecePos + currentOffset;
            if (BoardExtra.isValidSquareCoordinate(endCandidate)) {
                final Piece pieceAtDestination = board.getPiece(endCandidate);
                if (pieceAtDestination == null) {
                    legalMoves.add(new MajorMove(board, this, endCandidate));
                } else {
                    final Color pieceAtDestinationColor = pieceAtDestination.getPieceColor();
                    if (this.pieceColor != pieceAtDestinationColor) {
                        legalMoves.add(new MajorAttackMove(board, this, endCandidate,
                                pieceAtDestination));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public int locationBonus() {
        return this.pieceColor.knightBonus(this.piecePos);
    }

    @Override
    public Knight movePiece(final Move move) {
        return PieceExtra.INSTANCE.getMovedKnight(move.getMovedPiece().getPieceColor(), move.getEndCoordinate());
    }

    @Override
    public String toString() {
        return "\u2658";
    }

    private static boolean isFirstFileRemoval(final int currentPosition,
                                              final int candidateOffset) {
        return BoardExtra.INSTANCE.FILE_A.get(currentPosition) && ((candidateOffset == -17) ||
                (candidateOffset == -10) || (candidateOffset == 6) || (candidateOffset == 15));
    }

    private static boolean isSecondFileRemoval(final int currentPosition,
                                               final int candidateOffset) {
        return BoardExtra.INSTANCE.FILE_B.get(currentPosition) && ((candidateOffset == -10) || (candidateOffset == 6));
    }

    private static boolean isSeventhFileRemoval(final int currentPosition,
                                                final int candidateOffset) {
        return BoardExtra.INSTANCE.FILE_G.get(currentPosition) && ((candidateOffset == -6) || (candidateOffset == 10));
    }

    private static boolean isEighthFileRemoval(final int currentPosition,
                                               final int candidateOffset) {
        return BoardExtra.INSTANCE.FILE_H.get(currentPosition) && ((candidateOffset == -15) || (candidateOffset == -6) ||
                (candidateOffset == 10) || (candidateOffset == 17));
    }

}