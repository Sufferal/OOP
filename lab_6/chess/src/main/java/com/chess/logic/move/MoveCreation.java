package com.chess.logic.move;

import com.chess.logic.board.Board;

public class MoveCreation {

    private static final Move INVALID_MOVE = new InvalidMove();

    private MoveCreation() {
        throw new RuntimeException("You cannot instantiate this class!");
    }

    public static Move getInvalidMove() {
        return INVALID_MOVE;
    }

    public static Move createMove(final Board board,
                                  final int currentCoordinate,
                                  final int endCoordinate) {
        for (final Move move : board.getAllLegalMoves()) {
            if (move.getCurrentCoordinate() == currentCoordinate &&
                    move.getEndCoordinate() == endCoordinate) {
                return move;
            }
        }
        return INVALID_MOVE;
    }
}
