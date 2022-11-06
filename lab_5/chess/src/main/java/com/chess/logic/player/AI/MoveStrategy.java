package com.chess.logic.player.AI;

import com.chess.logic.board.Board;
import com.chess.logic.board.Move;

public interface MoveStrategy {

    Move execute(Board board);
}
