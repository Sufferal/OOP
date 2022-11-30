package com.chess.logic.MVC;

import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.move.MoveCreation;
import com.chess.logic.move.MoveShift;

public class ChessModel {
    private Board board;

    public Board getBoard() {
        return this.board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean isGameDone() {
        return board.currentPlayer().isInCheckMate() ||
                board.currentPlayer().isInStaleMate();
    }

    public void makeMove(String[] squares) {
        MoveShift tmpMove = board.currentPlayer()
            .makeMove(MoveCreation.createMove(board,
                    BoardExtra.INSTANCE.getCoordinateAtPos(squares[0]),
                    BoardExtra.INSTANCE.getCoordinateAtPos(squares[1])));
        this.board = tmpMove.getToBoard();
    }
}
