package com.chess.logic.move;

import com.chess.logic.board.Board;

public final class MoveShift {

    private final Board fromBoard;
    private final Board toBoard;
    private final Move shiftMove;
    private final MoveStatus moveStatus;

    public MoveShift(final Board fromBoard,
                     final Board toBoard,
                     final Move shiftMove,
                     final MoveStatus moveStatus) {
        this.fromBoard = fromBoard;
        this.toBoard = toBoard;
        this.shiftMove = shiftMove;
        this.moveStatus = moveStatus;
    }

    public Board getFromBoard() {
        return this.fromBoard;
    }
    public Board getToBoard() {
        return this.toBoard;
    }
    public Move getShiftMove() {
        return this.shiftMove;
    }
    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
}
