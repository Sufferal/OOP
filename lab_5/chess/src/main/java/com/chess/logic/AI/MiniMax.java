package com.chess.logic.AI;

import com.chess.logic.board.Board;
import com.chess.logic.board.BoardExtra;
import com.chess.logic.board.Move;
import com.chess.logic.board.MoveShift;

import java.util.concurrent.atomic.AtomicLong;

import static com.chess.logic.board.Move.*;

public final class MiniMax implements MoveCalculation {

    private final BoardEvaluator evaluator;
    private final int searchDepth;
    private long boardsEvaluated;
    private long executionTime;
    private FreqTableRow[] freqTable;
    private int freqTableIndex;

    public MiniMax(final int searchDepth) {
        this.evaluator = StandardBoardEvaluator.get();
        this.boardsEvaluated = 0;
        this.searchDepth = searchDepth;
    }

    public Move realize(final Board board) {
        final long startTime = System.currentTimeMillis();
        Move bestMove = MoveCreation.getInvalidMove();

        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;

//        System.out.println(board.currentPlayer() + " THINKING with depth = " +this.searchDepth);

        this.freqTable = new FreqTableRow[board.currentPlayer().getLegalMoves().size()];
        this.freqTableIndex = 0;

        int moveCounter = 1;
        final int numMoves = board.currentPlayer().getLegalMoves().size();
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveShift moveShift = board.currentPlayer().makeMove(move);
            if (moveShift.getMoveStatus().isDone()) {
                final FreqTableRow row = new FreqTableRow(move);
                this.freqTable[this.freqTableIndex] = row;
                currentValue = board.currentPlayer().getColor().isWhite() ?
                        min(moveShift.getToBoard(), this.searchDepth - 1) :
                        max(moveShift.getToBoard(), this.searchDepth - 1);
//                System.out.println("\t" + toString() + " analyzing move (" +moveCounter + "/" +numMoves+ ") " + move +
//                        " scores " + currentValue + " " +this.freqTable[this.freqTableIndex]);
                this.freqTableIndex++;
                if (board.currentPlayer().getColor().isWhite() &&
                        currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;
                } else if (board.currentPlayer().getColor().isBlack() &&
                        currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    bestMove = move;
                }
            } else {
//                System.out.println("\t" + toString() + " can't execute move (" +moveCounter+ "/" +numMoves+ ") " + move);
            }
            moveCounter++;
        }

        this.executionTime = System.currentTimeMillis() - startTime;
        if (this.executionTime < 1000) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.printf("%s AI chooses %s \ntime = %d ms\n", board.currentPlayer(), bestMove, this.executionTime);
        long total = 0;
        for (final FreqTableRow row : this.freqTable) {
            if(row != null) {
                total += row.getCount();
            }
        }

//        if(this.boardsEvaluated != total) {
//            System.out.println("[ERROR]: ");
//        }

        return bestMove;
    }

    private int min(final Board board,
                    final int depth) {
        if(depth == 0) {
            this.boardsEvaluated++;
            this.freqTable[this.freqTableIndex].increment();
            return this.evaluator.evaluate(board, depth);
        }
        if(isEndGameScenario(board)) {
            return this.evaluator.evaluate(board, depth);
        }
        int lowestSeenValue = Integer.MAX_VALUE;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveShift moveShift = board.currentPlayer().makeMove(move);
            if (moveShift.getMoveStatus().isDone()) {
                final int currentValue = max(moveShift.getToBoard(), depth - 1);
                if (currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                }
            }
        }
        return lowestSeenValue;
    }

    private int max(final Board board,
                    final int depth) {
        if(depth == 0) {
            this.boardsEvaluated++;
            this.freqTable[this.freqTableIndex].increment();
            return this.evaluator.evaluate(board, depth);
        }
        if(isEndGameScenario(board)) {
            return this.evaluator.evaluate(board, depth);
        }
        int highestSeenValue = Integer.MIN_VALUE;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveShift moveShift = board.currentPlayer().makeMove(move);
            if (moveShift.getMoveStatus().isDone()) {
                final int currentValue = min(moveShift.getToBoard(), depth - 1);
                if (currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                }
            }
        }
        return highestSeenValue;
    }

    private static boolean isEndGameScenario(final Board board) {
        return board.currentPlayer().isInCheckMate() || board.currentPlayer().isInStaleMate();
    }

    private static class FreqTableRow {

        private final Move move;
        private final AtomicLong count;

        FreqTableRow(final Move move) {
            this.count = new AtomicLong();
            this.move = move;
        }

        long getCount() {
            return this.count.get();
        }

        void increment() {
            this.count.incrementAndGet();
        }

        @Override
        public String toString() {
            return BoardExtra.INSTANCE.getPosAtCoordinate(this.move.getCurrentCoordinate()) +
                    BoardExtra.INSTANCE.getPosAtCoordinate(this.move.getEndCoordinate()) + " : " +this.count;
        }
    }

    @Override
    public String toString() {
        return "MiniMax";
    }

    @Override
    public long getNumBoardsEvaluated() {
        return this.boardsEvaluated;
    }
}
