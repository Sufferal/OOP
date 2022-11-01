package com.chess.logic.pieces;

import com.chess.logic.GeneralColor;
import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.board.Move;
import com.chess.logic.board.Square;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.logic.board.Move.*;

public class Knight extends Piece{

    private final static int[] CANDIDATE_MOVES = { -17, -15, -10, -6, 6, 10, 15, 17 };

    public Knight(final int pieceCoordinate, final GeneralColor pieceColor) {
        super(PieceType.Knight, pieceCoordinate, pieceColor);
    }

    @Override
    public Collection<Move> searchLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidate: CANDIDATE_MOVES) {
            final int endCoordinate = this.pieceCoordinate + currentCandidate;

            if(BoardExtra.isValidSquareCoordinate(endCoordinate)) {

                if(isFirstFileRemoval(this.pieceCoordinate, currentCandidate) ||
                        isSecondFileRemoval(this.pieceCoordinate, currentCandidate) ||
                        isSeventhFileRemoval(this.pieceCoordinate, currentCandidate) ||
                        isEighthFileRemoval(this.pieceCoordinate, currentCandidate)) {
                    continue;
                }

                final Square endSquare = board.getSquare(endCoordinate);

                if(!endSquare.isSquareOccupied()) {
                    legalMoves.add(new MajorMove(board, this, endCoordinate));
                } else {
                    final Piece endPiece = endSquare.getPiece();
                    final GeneralColor endPieceColor = endPiece.getPieceColor();

                    if(this.pieceColor != endPieceColor) {
                        legalMoves.add(new AttackMove(board, this, endCoordinate, endPiece));
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstFileRemoval(final int currentPos, final int posOffset) {
        return BoardExtra.FIRST_FILE[currentPos] && (posOffset == -17) || (posOffset == -10) || (posOffset == 6) || (posOffset == 15);
    }

    private static boolean isSecondFileRemoval(final int currentPos, final int posOffset) {
        return BoardExtra.SECOND_FILE[currentPos] && (posOffset == -10) || (posOffset == 6);
    }

    private static boolean isSeventhFileRemoval(final int currentPos, final int posOffset) {
        return BoardExtra.SEVENTH_FILE[currentPos] && (posOffset == -6) || (posOffset == 10);
    }

    private static boolean isEighthFileRemoval(final int currentPos, final int posOffset) {
        return BoardExtra.EIGHTH_FILE[currentPos] && (posOffset == -15) || (posOffset == -6) || (posOffset == 10) || (posOffset == 17);
    }

    @Override
    public String toString() {
        return PieceType.Knight.toString();
    }
}
