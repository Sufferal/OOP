package com.chess.logic.player.AI;

import com.chess.logic.board.Board;
import com.chess.logic.board.Move;
import com.chess.logic.player.MoveTransition;

public class MiniMax implements MoveStrategy{

    private final BoardEvaluator boardEvaluator;
    private final int searchDepth;

    public MiniMax(final int searchDepth) {
        this.boardEvaluator = new ClassicBoardEvaluator();
        this.searchDepth = searchDepth;
    }

    @Override
    public String toString() {
        return "MiniMax";
    }

    @Override
    public Move execute(Board board) {

        final long startTime = System.currentTimeMillis();

        Move bestMove = null;
        int highestValue = Integer.MIN_VALUE;
        int lowestValue = Integer.MIN_VALUE;
        int currentValue;

        System.out.println(board.currentPlayer() + " PROCESSING WITH DEPTH: " + searchDepth);

        int numMoves = board.currentPlayer().getLegalMoves().size();
        for (final Move move: board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);

            if (moveTransition.getMoveStatus().isDone()) {
                currentValue = board.currentPlayer().getColor().isWhite() ?
                        min(moveTransition.getTransitionBoard(), searchDepth - 1) :
                        max(moveTransition.getTransitionBoard(), searchDepth - 1);

                if (board.currentPlayer().getColor().isWhite() && currentValue >= highestValue) {
                    highestValue = currentValue;
                    bestMove = move;
                } else if (board.currentPlayer().getColor().isBlack() && currentValue <= lowestValue) {
                    lowestValue = currentValue;
                    bestMove = move;
                }
            }
        }

        final long executionTime = System.currentTimeMillis() - startTime;

        return bestMove;
    }

    public int min(final Board board, final int depth) {
        if (depth == 0 || isGameOver(board)) {
            return this.boardEvaluator.evaluate(board, depth);
        }
        int lowestValue = Integer.MAX_VALUE;
        for (final Move move: board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currentValue = max(moveTransition.getTransitionBoard(), depth - 1);
                if (currentValue <= lowestValue) {
                    lowestValue = currentValue;
                }
            }
        }

        return lowestValue;
    }


    public int max(final Board board, final int depth) {
        if (depth == 0 || isGameOver(board)) {
            return this.boardEvaluator.evaluate(board, depth);
        }
        int highestValue = Integer.MIN_VALUE;
        for (final Move move: board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currentValue = min(moveTransition.getTransitionBoard(), depth - 1);
                if (currentValue >= highestValue) {
                    highestValue = currentValue;
                }
            }
        }

        return highestValue;
    }

    private static boolean isGameOver(final Board board) {
        return board.currentPlayer().isInCheckmate() ||
                board.currentPlayer().isInStalemate();
    }
}
