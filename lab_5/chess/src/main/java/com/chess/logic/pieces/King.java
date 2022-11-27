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

public final class King extends Piece {

    private final static int[] MOVE_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 };
    private final boolean isCastled;
    private final boolean kingSideCastleCapable;
    private final boolean queenSideCastleCapable;

    public King(final Color color,
                final int piecePos,
                final boolean kingSideCastleCapable,
                final boolean queenSideCastleCapable) {
        super(PieceType.KING, color, piecePos, true);
        this.isCastled = false;
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
    }

    public King(final Color color,
                final int piecePos,
                final boolean isFirstMove,
                final boolean isCastled,
                final boolean kingSideCastleCapable,
                final boolean queenSideCastleCapable) {
        super(PieceType.KING, color, piecePos, isFirstMove);
        this.isCastled = isCastled;
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
    }

    public boolean isCastled() {
        return this.isCastled;
    }
    public boolean isKingSideCastleCapable() {
        return this.kingSideCastleCapable;
    }
    public boolean isQueenSideCastleCapable() {
        return this.queenSideCastleCapable;
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentOffset : MOVE_COORDINATES) {
            if (isFirstFileRemoval(this.piecePos, currentOffset) ||
                    isEighthFileRemoval(this.piecePos, currentOffset)) {
                continue;
            }
            final int endCandidate = this.piecePos + currentOffset;
            if (BoardExtra.isValidSquareCoordinate(endCandidate)) {
                final Piece pieceAtDestination = board.getPiece(endCandidate);
                if (pieceAtDestination == null) {
                    legalMoves.add(new MajorMove(board, this, endCandidate));
                } else {
                    final Color endPieceColor = pieceAtDestination.getPieceColor();
                    if (this.pieceColor != endPieceColor) {
                        legalMoves.add(new MajorAttackMove(board, this, endCandidate,
                                pieceAtDestination));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public String toString() {
        return "\u2654";
    }
    @Override
    public int locationBonus() {
        return this.pieceColor.kingBonus(this.piecePos);
    }
    @Override
    public King movePiece(final Move move) {
        return new King(this.pieceColor, move.getEndCoordinate(), false, move.isCastlingMove(), false, false);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof King)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        final King king = (King) other;
        return isCastled == king.isCastled;
    }

    @Override
    public int hashCode() {
        return (31 * super.hashCode()) + (isCastled ? 1 : 0);
    }

    private static boolean isFirstFileRemoval(final int currentCandidate,
                                              final int candidateEnd) {
        return BoardExtra.INSTANCE.FILE_A.get(currentCandidate)
                && ((candidateEnd == -9) || (candidateEnd == -1) ||
                (candidateEnd == 7));
    }

    private static boolean isEighthFileRemoval(final int currentCandidate,
                                               final int candidateEnd) {
        return BoardExtra.INSTANCE.FILE_H.get(currentCandidate)
                && ((candidateEnd == -7) || (candidateEnd == 1) ||
                (candidateEnd == 9));
    }
}