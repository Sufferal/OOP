package com.chess.logic;

import com.chess.logic.board.Board;

public class Game {
    public static void main(String[] args) {
        Board board = Board.createBoard();
        System.out.println(board);
    }
}
