package com.chess.logic.player.AI;

import com.chess.logic.board.Board;
import com.chess.logic.pieces.Piece;
import com.chess.logic.player.Player;

public final class ClassicBoardEvaluator implements BoardEvaluator {
    private static final int CHECK_EVAL = 25;
    private static final int CHECKMATE_EVAL = 99999;
    private static final int DEPTH_EVAL = 200;
    private static final int CASTLE_EVAL = 100;

    @Override
    public int evaluate(Board board, int depth) {
        return scorePlayer(board, board.whitePlayer(), depth) -
                scorePlayer(board, board.blackPlayer(), depth);
    }

    private int scorePlayer(final Board board, final Player player, final int depth) {
        return pieceValue(player) +
                mobilityEval(player) +
                checkEval(player) +
                checkmateEval(player, depth) +
                castleEval(player);
    }

    private static int castleEval(Player player) {
        return player.isCastled() ? CASTLE_EVAL : 0;
    }

    private static int checkmateEval(Player player, int depth) {
        return player.getOpponent().isInCheckmate() ? CHECKMATE_EVAL * depthEval(depth) : 0;
    }

    private static int depthEval(int depth) {
        return depth == 0 ? 1 : DEPTH_EVAL * depth;
    }

    private static int checkEval(Player player) {
        return player.getOpponent().isInCheck() ? 25 : 0;
    }

    private static int mobilityEval(Player player) {
        return player.getLegalMoves().size();
    }

    private static int pieceValue(final Player player) {
        int pieceValueScore = 0;
        for (final Piece piece: player.getActivePieces()) {
            pieceValueScore += piece.getPieceValue();
        }
        return pieceValueScore;
    }
}
