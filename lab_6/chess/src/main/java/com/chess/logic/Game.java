package com.chess.logic;

import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.board.Move;
import com.chess.logic.board.MoveShift;
import com.chess.logic.AI.MiniMax;
import com.chess.logic.AI.MoveCalculation;

import java.util.Objects;
import java.util.Scanner;

public class Game {
    public static void main(String[] args)  {
        Board board = Board.createStandardBoard();
//        Board board = Board.createStalematedBoard();
//        Board board = Board.createQueenRookCheckmateBoard();
//        Board board = Board.create2RooksCheckmateBoard();
//        Board board = Board.createPosition1Board();
//        Board board = Board.createPosition2Board();

        System.out.println("=======================");
        System.out.println("| 1 - Human vs Human   |");
        System.out.println("| 2 - Human vs AI      |");
        System.out.println("| 3 - AI vs AI         |");
        System.out.println("=======================");
        Scanner matchChoiceScanner = new Scanner(System.in);
        System.out.print("Match choice: ");
        int matchChoice;

        matchChoice = matchChoiceScanner.nextInt();

        switch (matchChoice) {
            case 1 -> {
                System.out.println("Human vs Human");
                System.out.println(board);
                Scanner startSquareScanner = new Scanner(System.in);
                System.out.print(board.currentPlayer() + " enter start square: ");
                String startSquare = startSquareScanner.nextLine();
                Scanner endSquareScanner = new Scanner(System.in);
                System.out.print(board.currentPlayer() + " enter end square: ");
                String endSquare = endSquareScanner.nextLine();
                if (Objects.equals(startSquare, "stop") || Objects.equals(endSquare, "stop")) {
                    return;
                }
                final MoveShift move1 = board.currentPlayer()
                        .makeMove(Move.MoveCreation.createMove(board, BoardExtra.INSTANCE.getCoordinateAtPos(startSquare),
                                BoardExtra.INSTANCE.getCoordinateAtPos(endSquare)));
                Board tmpBoard = move1.getToBoard();
                System.out.println(tmpBoard);
                while (true) {
                    if (Objects.equals(startSquare, "stop") || Objects.equals(endSquare, "stop")) {
                        break;
                    }
                    if (tmpBoard.currentPlayer().isInCheckMate()) {
                        System.out.println("[CHECKMATE]: " +tmpBoard.currentPlayer().getOpponent() + " won by checkmate!");
                        break;
                    }
                    if (tmpBoard.currentPlayer().isInStaleMate()) {
                        System.out.println("[STALEMATE]: " +tmpBoard.currentPlayer() + " drew by stalemate!");
                        break;
                    }
                    if (tmpBoard.currentPlayer().isInCheck()) {
                        System.out.println("[CHECK]: " + tmpBoard.currentPlayer() + " is in check!");
                    }
                    System.out.print(tmpBoard.currentPlayer() + " start square: ");
                    startSquare = startSquareScanner.nextLine();
                    System.out.print(tmpBoard.currentPlayer() + " end square: ");
                    endSquare = endSquareScanner.nextLine();
                    MoveShift tmpMove = tmpBoard
                            .currentPlayer()
                            .makeMove(Move.MoveCreation
                                    .createMove(tmpBoard, BoardExtra.INSTANCE.getCoordinateAtPos(startSquare),
                                            BoardExtra.INSTANCE.getCoordinateAtPos(endSquare)));
                    tmpBoard = tmpMove.getToBoard();
                    System.out.println(tmpBoard);
                }
            }
            case 2 -> {
                System.out.println("Human vs AI");
                System.out.println(board);

                System.out.println("Enter AI depth: ");
                Scanner AIDepthScanner = new Scanner(System.in);
                int AIDepth = AIDepthScanner.nextInt();

                Scanner startSquareScanner = new Scanner(System.in);
                System.out.print(board.currentPlayer() + " enter start square: ");
                String startSquare = startSquareScanner.nextLine();
                Scanner endSquareScanner = new Scanner(System.in);
                System.out.print(board.currentPlayer() + " enter end square: ");
                String endSquare = endSquareScanner.nextLine();
                if (Objects.equals(startSquare, "stop") || Objects.equals(endSquare, "stop")) {
                    return;
                }
                final MoveShift move1 = board.currentPlayer()
                        .makeMove(Move.MoveCreation.createMove(board, BoardExtra.INSTANCE.getCoordinateAtPos(startSquare),
                                BoardExtra.INSTANCE.getCoordinateAtPos(endSquare)));
                Board tmpBoard = move1.getToBoard();
                System.out.println(tmpBoard);

                MoveCalculation minMax = new MiniMax(AIDepth);
                Move bestMove = minMax.realize(tmpBoard);

                MoveShift tmpMove = tmpBoard
                        .currentPlayer()
                        .makeMove(Move.MoveCreation
                                .createMove(tmpBoard, bestMove.getCurrentCoordinate(), bestMove.getEndCoordinate()));

                tmpBoard = tmpMove.getToBoard();
                System.out.println(tmpBoard);
                boolean isHumanTurn = true;

                while (true) {
                    if (Objects.equals(startSquare, "stop") || Objects.equals(endSquare, "stop")) {
                        break;
                    }
                    if (tmpBoard.currentPlayer().isInCheckMate()) {
                        System.out.println("[CHECKMATE]: " +tmpBoard.currentPlayer().getOpponent() + " won by checkmate!");
                        break;
                    }
                    if (tmpBoard.currentPlayer().isInStaleMate()) {
                        System.out.println("[STALEMATE]: " +tmpBoard.currentPlayer() + " drew by stalemate!");
                        break;
                    }
                    if (tmpBoard.currentPlayer().isInCheck()) {
                        System.out.println("[CHECK]: " + tmpBoard.currentPlayer() + " is in check!");
                    }
                    if (isHumanTurn) {
                        System.out.print(tmpBoard.currentPlayer() + " start square: ");
                        startSquare = startSquareScanner.nextLine();
                        System.out.print(tmpBoard.currentPlayer() + " end square: ");
                        endSquare = endSquareScanner.nextLine();

                        tmpMove = tmpBoard
                                .currentPlayer()
                                .makeMove(Move.MoveCreation
                                        .createMove(tmpBoard, BoardExtra.INSTANCE.getCoordinateAtPos(startSquare),
                                                BoardExtra.INSTANCE.getCoordinateAtPos(endSquare)));
                        isHumanTurn = false;
                    } else {
                        bestMove = minMax.realize(tmpBoard);

                        tmpMove = tmpBoard
                                .currentPlayer()
                                .makeMove(Move.MoveCreation
                                        .createMove(tmpBoard,
                                                bestMove.getCurrentCoordinate(),
                                                bestMove.getEndCoordinate()));
                        isHumanTurn = true;
                    }

                    tmpBoard = tmpMove.getToBoard();
                    System.out.println(tmpBoard);
                }
            }
            case 3 -> {
                System.out.println("AI vs AI");
                System.out.println(board);
                Board tmpBoard = board;

                System.out.println("Enter White AI depth: ");
                Scanner AIWhiteDepthScanner = new Scanner(System.in);
                int AIWhiteDepth = AIWhiteDepthScanner.nextInt();

                System.out.println("Enter Black AI depth: ");
                Scanner AIBlackDepthScanner = new Scanner(System.in);
                int AIBlackDepth = AIBlackDepthScanner.nextInt();

                MoveCalculation minMaxWhite = new MiniMax(AIWhiteDepth);
                Move bestMoveWhite = minMaxWhite.realize(tmpBoard);
                MoveShift tmpMoveWhite = tmpBoard
                        .currentPlayer()
                        .makeMove(Move.MoveCreation
                                .createMove(tmpBoard, bestMoveWhite.getCurrentCoordinate(),
                                        bestMoveWhite.getEndCoordinate()));
                tmpBoard = tmpMoveWhite.getToBoard();
                System.out.println(tmpBoard);


                MoveCalculation minMaxBlack = new MiniMax(AIBlackDepth);
                Move bestMoveBlack = minMaxBlack.realize(tmpBoard);
                MoveShift tmpMoveBlack = tmpBoard
                        .currentPlayer()
                        .makeMove(Move.MoveCreation
                                .createMove(tmpBoard, bestMoveBlack.getCurrentCoordinate(), bestMoveBlack.getEndCoordinate()));
                tmpBoard = tmpMoveBlack.getToBoard();
                System.out.println(tmpBoard);

                boolean isWhiteAITurn = true;

                while (true) {
                    if (tmpBoard.currentPlayer().isInCheckMate()) {
                        System.out.println("[CHECKMATE]: " +tmpBoard.currentPlayer().getOpponent() + " won by checkmate!");
                        break;
                    }
                    if (tmpBoard.currentPlayer().isInStaleMate()) {
                        System.out.println("[STALEMATE]: " +tmpBoard.currentPlayer() + " drew by stalemate!");
                        break;
                    }
                    if (tmpBoard.currentPlayer().isInCheck()) {
                        System.out.println("[CHECK]: " + tmpBoard.currentPlayer() + " is in check!");
                    }


                    if (isWhiteAITurn) {
                        bestMoveWhite = minMaxWhite.realize(tmpBoard);
                        tmpMoveWhite = tmpBoard
                                .currentPlayer()
                                .makeMove(Move.MoveCreation
                                        .createMove(tmpBoard, bestMoveWhite.getCurrentCoordinate(),
                                                bestMoveWhite.getEndCoordinate()));
                        tmpBoard = tmpMoveWhite.getToBoard();
                        isWhiteAITurn = false;
                    } else {
                        bestMoveBlack = minMaxBlack.realize(tmpBoard);
                        tmpMoveBlack = tmpBoard
                                .currentPlayer()
                                .makeMove(Move.MoveCreation
                                        .createMove(tmpBoard, bestMoveBlack.getCurrentCoordinate(),
                                                bestMoveBlack.getEndCoordinate()));
                        tmpBoard = tmpMoveBlack.getToBoard();
                        isWhiteAITurn = true;
                    }

                    System.out.println(tmpBoard);
                }
            }
        }
    }
}
