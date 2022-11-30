package com.chess.logic.MVC;

import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.move.MoveCreation;
import com.chess.logic.move.MoveShift;

public class ChessController {
    private ChessModel model;
    private ChessView view;

    public ChessController(ChessModel model, ChessView view) {
        this.model = model;
        this.view = view;
    }

    public void updateView() {
        view.printBoard(model.getBoard());
    }

    public void updateModel() {
        model.makeMove(view.enterMove(model.getBoard()));
    }

    public int getMatchChoice() { return view.getMatchChoice(); }
    public void showGameStatus() {
        view.showGameStatus(model.getBoard());
    }

    public Board getBoard() {
        return model.getBoard();
    }
    public void setBoard(Board board) {
        model.setBoard(board);
    }

    public boolean isGameDone() {
        return model.getBoard().currentPlayer().isInCheckMate() ||
                model.getBoard().currentPlayer().isInStaleMate();
    }
}
