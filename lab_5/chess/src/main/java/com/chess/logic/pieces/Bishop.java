package com.chess.logic.pieces;

import com.chess.logic.GeneralColor;
import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.board.Move;
import com.chess.logic.board.Square;
import com.google.common.collect.ImmutableList;

import static com.chess.logic.board.Move.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bishop extends Piece{

    private final static int[] CANDIDATE_MOVES = { -9, -7, 7, 9 };

    public Bishop(final int pieceCoordinate, final GeneralColor pieceColor) {
        super(PieceType.Bishop, pieceCoordinate, pieceColor);
    }

    @Override
    public Collection<Move> searchLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int candidateOffset: CANDIDATE_MOVES) {
            int endCoordinate = this.pieceCoordinate;

            while(BoardExtra.isValidSquareCoordinate(endCoordinate)) {
                if(isFirstFileRemoval(endCoordinate, candidateOffset) ||
                    isEighthFileRemoval(endCoordinate, candidateOffset)) {
                    break;
                }

                endCoordinate += candidateOffset;
                if(BoardExtra.isValidSquareCoordinate(endCoordinate)) {
                    final Square endSquare = board.getSquare(endCoordinate);

                    if(!endSquare.isSquareOccupied()) {
                        legalMoves.add(new MajorMove(board, this, endCoordinate));
                    } else {
                        final Piece endPiece = endSquare.getPiece();
                        final GeneralColor endPieceColor = endPiece.getPieceColor();

                        if(this.pieceColor != endPieceColor) {
                            legalMoves.add(new AttackMove(board, this, endCoordinate, endPiece));
                        }
                        break;
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstFileRemoval(final int currentPos, final int posOffset) {
        return BoardExtra.FIRST_FILE[currentPos] && (posOffset == -9) || (posOffset == 7);
    }

    private static boolean isEighthFileRemoval(final int currentPos, final int posOffset) {
        return BoardExtra.EIGHTH_FILE[currentPos] && (posOffset == -7) || (posOffset == 9);
    }

    @Override
    public String toString() {
        return PieceType.Bishop.toString();
    }
}
