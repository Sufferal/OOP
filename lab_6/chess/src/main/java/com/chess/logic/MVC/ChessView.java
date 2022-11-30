package com.chess.logic.MVC;

import com.chess.logic.board.Board;

import java.util.Objects;
import java.util.Scanner;

public class ChessView {
    public void printBoard(Board board) {
        System.out.println(board);
    }

    public int getMatchChoice() {
        System.out.println("=======================");
        System.out.println("| 1 - Human vs Human   |");
        System.out.println("| 2 - Human vs AI      |");
        System.out.println("| 3 - AI vs AI         |");
        System.out.println("=======================");

        Scanner matchChoiceScanner = new Scanner(System.in);
        System.out.print("Match choice: ");

        return matchChoiceScanner.nextInt();
    }

    public String[] enterMove(Board board) {
        Scanner startSquareScanner = new Scanner(System.in);
        System.out.print(board.currentPlayer() + " enter start square: ");
        String startSquare = startSquareScanner.nextLine();
        Scanner endSquareScanner = new Scanner(System.in);
        System.out.print(board.currentPlayer() + " enter end square: ");
        String endSquare = endSquareScanner.nextLine();

        if (Objects.equals(startSquare, "stop") || Objects.equals(endSquare, "stop")) {
            throw new RuntimeException("Invalid square");
        }

        return new String[]{startSquare, endSquare};
    }

    public void showGameStatus(Board board) {
        if (board.currentPlayer().isInCheckMate()) {
            System.out.println("[CHECKMATE]: " + board.currentPlayer().getOpponent() + " won by checkmate!");
        }
        if (board.currentPlayer().isInStaleMate()) {
            System.out.println("[STALEMATE]: " + board.currentPlayer() + " drew by stalemate!");
        }
    }
}
