package com.chess.logic.MVC;

import com.chess.logic.board.Board;

public class ChessMain {
    public static void main(String[] args)  {
        ChessModel model = new ChessModel();
        ChessView view = new ChessView();
        ChessController controller = new ChessController(model, view);

//        controller.getMatchChoice();
        controller.setBoard(Board.createStandardBoard());

        while (!controller.isGameDone()) {
            controller.updateView();
            controller.updateModel();
        }

        if(controller.isGameDone()) {
            controller.updateView();
            controller.showGameStatus();
        }
    }
}