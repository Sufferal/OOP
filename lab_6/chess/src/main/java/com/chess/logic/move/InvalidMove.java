package com.chess.logic.move;

import com.chess.logic.board.Board;

class InvalidMove
        extends Move {

    InvalidMove() {
        super(null, -1);
    }

    @Override
    public int getCurrentCoordinate() {
        return -1;
    }

    @Override
    public int getEndCoordinate() {
        return -1;
    }

    @Override
    public Board realize() {
        throw new RuntimeException("You cannot realize invalid move!");
    }

    @Override
    public String toString() {
        return "Invalid Move";
    }
}
