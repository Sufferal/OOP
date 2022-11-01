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

public class King extends Piece{

    private final static int[] CANDIDATE_MOVES = { -9, -8, -7, -1, 1, 7, 8, 9 };

    public King(int pieceCoordinate, GeneralColor pieceColor) {
        super(pieceCoordinate, pieceColor);
    }

    @Override
    public Collection<Move> searchLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int candidateOffset: CANDIDATE_MOVES) {
            final int endCoordinate = this.pieceCoordinate + candidateOffset;

            if(isFirstFileRemoval(this.pieceCoordinate, candidateOffset) ||
            isEighthFileRemoval(this.pieceCoordinate, candidateOffset)) {
                continue;
            }

            if(BoardExtra.isValidSquareCoordinate(endCoordinate)) {
                final Square endSquare = board.getSquare(endCoordinate);

                if(!endSquare.isSquareOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, endCoordinate));
                } else {
                    final Piece endPiece = endSquare.getPiece();
                    final GeneralColor endPieceColor = endPiece.getPieceColor();

                    if(this.pieceColor != endPieceColor) {
                        legalMoves.add(new Move.AttackMove(board, this, endCoordinate, endPiece));
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstFileRemoval(final int currentPos, final int posOffset) {
        return BoardExtra.FIRST_FILE[currentPos] && (posOffset == -9) || (posOffset == -1) || (posOffset == 7);
    }

    private static boolean isEighthFileRemoval(final int currentPos, final int posOffset) {
        return BoardExtra.EIGHTH_FILE[currentPos] && (posOffset == -7) || (posOffset == 1) || (posOffset == 9);
    }

}
