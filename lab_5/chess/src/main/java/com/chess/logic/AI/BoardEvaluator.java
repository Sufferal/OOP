package com.chess.logic.AI;

import com.chess.logic.board.Board;

public interface BoardEvaluator {
    int evaluate(Board board, int depth);
}
