package com.chess.logic;

import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.board.Move;
import com.chess.logic.player.MoveTransition;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;

public class TestBoard {
    @Test
    public void initialBoard() {
        final Board board = Board.createBoard();

        // legal moves
        assertEquals(board.currentPlayer().getLegalMoves().size(), 20);
        assertEquals(board.currentPlayer().getOpponent().getLegalMoves().size(), 20);

        // status
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckmate());
        assertFalse(board.currentPlayer().isCastled());
        assertFalse(board.currentPlayer().isInStalemate());

        // opponent
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());

        // status
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckmate());
        assertFalse(board.currentPlayer().getOpponent().isCastled());
        assertFalse(board.currentPlayer().getOpponent().isInStalemate());
    }

    @Test
    public void foolsMate() {
        final Board board = Board.createBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardExtra.getCoordinateAtPosition("f2"),
                        BoardExtra.getCoordinateAtPosition("f3")));
        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getTransitionBoard().currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardExtra.getCoordinateAtPosition("e7"),
                        BoardExtra.getCoordinateAtPosition("e5")));
//        assertTrue(t2.getMoveStatus().isDone());
    }
}
