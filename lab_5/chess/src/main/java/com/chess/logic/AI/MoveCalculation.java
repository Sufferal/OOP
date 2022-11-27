package com.chess.logic.AI;

import com.chess.logic.board.Board;
import com.chess.logic.board.Move;

public interface MoveCalculation {
    long getNumBoardsEvaluated();
    Move realize(Board board);
}
