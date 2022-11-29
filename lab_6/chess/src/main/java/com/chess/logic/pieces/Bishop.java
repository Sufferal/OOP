package com.chess.logic.pieces;

import com.chess.logic.Color;
import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.move.Move;
import com.chess.logic.move.piece.MajorAttackMove;
import com.chess.logic.move.piece.MajorMove;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class Bishop extends Piece {

    private final static int[] MOVE_COORDINATES = { -9, -7, 7, 9 };

    public Bishop(final Color color,
                  final int piecePos) {
        super(PieceType.BISHOP, color, piecePos, true);
    }

    public Bishop(final Color color,
                  final int piecePos,
                  final boolean isFirstMove) {
        super(PieceType.BISHOP, color, piecePos, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentOffset : MOVE_COORDINATES) {
            int endCandidate = this.piecePos;
            while (BoardExtra.isValidSquareCoordinate(endCandidate)) {
                if (isFirstFileRemoval(currentOffset, endCandidate) ||
                        isEighthFileRemoval(currentOffset, endCandidate)) {
                    break;
                }
                endCandidate += currentOffset;
                if (BoardExtra.isValidSquareCoordinate(endCandidate)) {
                    final Piece pieceAtDestination = board.getPiece(endCandidate);
                    if (pieceAtDestination == null) {
                        legalMoves.add(new MajorMove(board, this, endCandidate));
                    }
                    else {
                        final Color endPieceColor = pieceAtDestination.getPieceColor();
                        if (this.pieceColor != endPieceColor) {
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
        return this.pieceColor.bishopBonus(this.piecePos);
    }

    @Override
    public Bishop movePiece(final Move move) {
        return PieceExtra.INSTANCE.getMovedBishop(move.getMovedPiece().getPieceColor(), move.getEndCoordinate());
    }

    @Override
    public String toString() {
        return "\u2657";
    }

    private static boolean isFirstFileRemoval(final int currentCandidate,
                                              final int candidateEnd) {
        return (BoardExtra.INSTANCE.FILE_A.get(candidateEnd) &&
                ((currentCandidate == -9) || (currentCandidate == 7)));
    }

    private static boolean isEighthFileRemoval(final int currentCandidate,
                                               final int candidateEnd) {
        return BoardExtra.INSTANCE.FILE_H.get(candidateEnd) &&
                ((currentCandidate == -7) || (currentCandidate == 9));
    }

}