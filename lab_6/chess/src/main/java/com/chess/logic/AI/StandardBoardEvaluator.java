package com.chess.logic.AI;

import com.chess.logic.board.Board;
import com.chess.logic.move.Move;
import com.chess.logic.pieces.Piece;
import com.chess.logic.player.Player;
import com.google.common.annotations.VisibleForTesting;

import static com.chess.logic.pieces.Piece.PieceType.BISHOP;

public final class StandardBoardEvaluator
        implements BoardEvaluator {

    private final static int CHECK_MATE_BONUS = 10000;
    private final static int CHECK_BONUS = 45;
    private final static int CASTLE_BONUS = 25;
    private final static int MOBILITY_MULTIPLIER = 5;
    private final static int ATTACK_MULTIPLIER = 1;
    private final static int TWO_BISHOPS_BONUS = 25;
    private static final StandardBoardEvaluator INSTANCE = new StandardBoardEvaluator();

    private StandardBoardEvaluator() {
    }

    public static StandardBoardEvaluator get() {
        return INSTANCE;
    }

    @Override
    public int evaluate(final Board board,
                        final int depth) {
        return score(board.whitePlayer(), depth) - score(board.blackPlayer(), depth);
    }

    public String evaluationDetails(final Board board, final int depth) {
        return
                ("White Mobility : " + mobility(board.whitePlayer()) + "\n") +
                        "White kingThreats : " + kingThreats(board.whitePlayer(), depth) + "\n" +
                        "White attacks : " + attacks(board.whitePlayer()) + "\n" +
                        "White castle : " + castle(board.whitePlayer()) + "\n" +
                        "White pieceEval : " + pieceEvaluations(board.whitePlayer()) + "\n" +
                        "---------------------\n" +
                        "Black Mobility : " + mobility(board.blackPlayer()) + "\n" +
                        "Black kingThreats : " + kingThreats(board.blackPlayer(), depth) + "\n" +
                        "Black attacks : " + attacks(board.blackPlayer()) + "\n" +
                        "Black castle : " + castle(board.blackPlayer()) + "\n" +
                        "Black pieceEval : " + pieceEvaluations(board.blackPlayer()) + "\n" +
                        "Final Score = " + evaluate(board, depth);
    }

    @VisibleForTesting
    private static int score(final Player player,
                             final int depth) {
        return mobility(player) +
                kingThreats(player, depth) +
                attacks(player) +
                castle(player) +
                pieceEvaluations(player);
    }

    private static int attacks(final Player player) {
        int attackScore = 0;
        for(final Move move : player.getLegalMoves()) {
            if(move.isAttack()) {
                final Piece movedPiece = move.getMovedPiece();
                final Piece attackedPiece = move.getAttackedPiece();
                if(movedPiece.getPieceValue() <= attackedPiece.getPieceValue()) {
                    attackScore++;
                }
            }
        }
        return attackScore * ATTACK_MULTIPLIER;
    }

    private static int pieceEvaluations(final Player player) {
        int pieceValuationScore = 0;
        int numBishops = 0;
        for (final Piece piece : player.getActivePieces()) {
            pieceValuationScore += piece.getPieceValue() + piece.locationBonus();
            if(piece.getPieceType() == BISHOP) {
                numBishops++;
            }
        }
        return pieceValuationScore + (numBishops == 2 ? TWO_BISHOPS_BONUS : 0);
    }

    private static int mobility(final Player player) {
        return MOBILITY_MULTIPLIER * mobilityRatio(player);
    }

    private static int mobilityRatio(final Player player) {
        return (int)((player.getLegalMoves().size() * 10.0f) / player.getOpponent().getLegalMoves().size());
    }

    private static int kingThreats(final Player player,
                                   final int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS  * depthBonus(depth) : check(player);
    }

    private static int check(final Player player) {
        return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
    }
    private static int depthBonus(final int depth) {
        return depth == 0 ? 1 : 100 * depth;
    }
    private static int castle(final Player player) {
        return player.isCastled() ? CASTLE_BONUS : 0;
    }

}
