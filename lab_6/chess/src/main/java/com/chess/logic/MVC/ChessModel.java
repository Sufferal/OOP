package com.chess.logic.MVC;

import com.chess.logic.AI.MiniMax;
import com.chess.logic.AI.MoveCalculation;
import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.move.Move;
import com.chess.logic.move.MoveCreation;
import com.chess.logic.move.MoveShift;

public class ChessModel {
    private Board board;
    private MoveCalculation humanAI, whiteAI, blackAI;
    private boolean isOpponentTurn;

    public Board getBoard() {
        return this.board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean isOpponentTurn() {
        return this.isOpponentTurn;
    }
    public void setIsOpponentTurn(boolean isOpponentTurn) {
        this.isOpponentTurn = isOpponentTurn;
    }

    public boolean isGameDone() {
        return this.board.currentPlayer().isInCheckMate() ||
                this.board.currentPlayer().isInStaleMate();
    }

    public void makeMove(String[] squares) {
        MoveShift tmpMove = this.board.currentPlayer()
            .makeMove(MoveCreation.createMove(this.board,
                    BoardExtra.INSTANCE.getCoordinateAtPos(squares[0]),
                    BoardExtra.INSTANCE.getCoordinateAtPos(squares[1])));
        this.board = tmpMove.getToBoard();
    }

    public void makeAIMove(Move bestMove) {
        MoveShift tmpMove = this.board
                .currentPlayer()
                .makeMove(MoveCreation.createMove(this.board,
                        bestMove.getCurrentCoordinate(),
                        bestMove.getEndCoordinate()));
        this.board = tmpMove.getToBoard();
    }

    public void createHumanAI(int depth) {
        this.humanAI = new MiniMax(depth);
    }

    public void createWhiteAI(int depth) {
        this.whiteAI = new MiniMax(depth);
    }

    public void createBlackAI(int depth) {
        this.blackAI = new MiniMax(depth);
    }

    public MoveCalculation getHumanAI() {
        return this.humanAI;
    }

    public MoveCalculation getWhiteAI() {
        return this.whiteAI;
    }

    public MoveCalculation getBlackAI() {
        return this.blackAI;
    }
}
