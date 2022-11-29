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

public final class Rook extends Piece {

    private final static int[] MOVE_COORDINATES = { -8, -1, 1, 8 };

    public Rook(final Color color, final int piecePos) {
        super(PieceType.ROOK, color, piecePos, true);
    }

    public Rook(final Color color,
                final int piecePos,
                final boolean isFirstMove) {
        super(PieceType.ROOK, color, piecePos, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentOffset : MOVE_COORDINATES) {
            int endCandidate = this.piecePos;
            while (BoardExtra.isValidSquareCoordinate(endCandidate)) {
                if (isFileRemoval(currentOffset, endCandidate)) {
                    break;
                }
                endCandidate += currentOffset;
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
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public int locationBonus() {
        return this.pieceColor.rookBonus(this.piecePos);
    }

    @Override
    public Rook movePiece(final Move move) {
        return PieceExtra.INSTANCE.getMovedRook(move.getMovedPiece().getPieceColor(), move.getEndCoordinate());
    }

    @Override
    public String toString() {
        return "\u2656";
    }

    private static boolean isFileRemoval(final int currentCandidate,
                                         final int candidateDestinationCoordinate) {
        return (BoardExtra.INSTANCE.FILE_A.get(candidateDestinationCoordinate) && (currentCandidate == -1)) ||
                (BoardExtra.INSTANCE.FILE_H.get(candidateDestinationCoordinate) && (currentCandidate == 1));
    }

}