package com.chess.logic.MVC;

import com.chess.logic.board.Board;

public class ChessController {
    private final ChessModel model;
    private final ChessView view;

    public ChessController(ChessModel model, ChessView view) {
        this.model = model;
        this.view = view;
    }

    public void initModel() {
        switch (this.getMatchChoice()) {
            case 2 -> {
                model.createHumanAI(view.enterDepth());
                model.setIsOpponentTurn(false);
            }
            case 3 -> {
                model.createWhiteAI(view.enterDepth());
                model.createBlackAI(view.enterDepth());
                model.setIsOpponentTurn(false);
            }
            default -> throw new RuntimeException("Invalid match choice");
        }
    }

    public void updateView() {
        view.printBoard(model.getBoard());
    }

    public void updateModel() {
        switch (this.getMatchChoice()) {
            case 1 -> model.makeMove(view.enterMove(model.getBoard()));
            case 2 -> {
                if(model.isOpponentTurn()) {
                    model.makeAIMove(model.getHumanAI().realize(model.getBoard()));
                    model.setIsOpponentTurn(false);
                } else {
                    model.makeMove(view.enterMove(model.getBoard()));
                    model.setIsOpponentTurn(true);
                }
            }
            case 3 -> {
                if(model.isOpponentTurn()) {
                    model.makeAIMove(model.getBlackAI().realize(model.getBoard()));
                    model.setIsOpponentTurn(false);
                } else {
                    model.makeAIMove(model.getWhiteAI().realize(model.getBoard()));
                    model.setIsOpponentTurn(true);
                }
            }
            default -> throw new RuntimeException("Invalid match choice");
        }
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
        return model.isGameDone();
    }

    public void enterMatchChoice() {
        view.enterMatchChoice();
    }
}
