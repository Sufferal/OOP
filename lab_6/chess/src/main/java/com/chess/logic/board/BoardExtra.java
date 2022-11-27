package com.chess.logic.board;

import com.chess.logic.pieces.King;
import com.chess.logic.pieces.Piece;

import java.util.*;

import static com.chess.logic.board.Move.MoveCreation;

public enum BoardExtra {

    INSTANCE;

    public final List<Boolean> FILE_A = createFile(0);
    public final List<Boolean> FILE_B = createFile(1);
    public final List<Boolean> FILE_C = createFile(2);
    public final List<Boolean> FILE_D = createFile(3);
    public final List<Boolean> FILE_E = createFile(4);
    public final List<Boolean> FILE_F = createFile(5);
    public final List<Boolean> FILE_G = createFile(6);
    public final List<Boolean> FILE_H = createFile(7);

    public final List<Boolean> RANK_8 = createRank(0);
    public final List<Boolean> RANK_7 = createRank(8);
    public final List<Boolean> RANK_6 = createRank(16);
    public final List<Boolean> RANK_5 = createRank(24);
    public final List<Boolean> RANK_4 = createRank(32);
    public final List<Boolean> RANK_3 = createRank(40);
    public final List<Boolean> RANK_2 = createRank(48);
    public final List<Boolean> RANK_1 = createRank(56);

    public final List<String> CHESS_NOTATION = createStandardChessNotation();
    public final Map<String, Integer> POS_TO_COORDINATES = createPosToCoordinates();

    public static final int SQUARES_PER_RANK = 8;
    public static final int SQUARES_NUM = 64;

    private static List<Boolean> createFile(int columnNumber) {
        final Boolean[] column = new Boolean[SQUARES_NUM];
        for(int i = 0; i < column.length; i++) {
            column[i] = false;
        }
        do {
            column[columnNumber] = true;
            columnNumber += SQUARES_PER_RANK;
        } while(columnNumber < SQUARES_NUM);
        return Collections.unmodifiableList(Arrays.asList((column)));
    }

    private static List<Boolean> createRank(int rowNumber) {
        final Boolean[] row = new Boolean[SQUARES_NUM];
        for(int i = 0; i < row.length; i++) {
            row[i] = false;
        }
        do {
            row[rowNumber] = true;
            rowNumber++;
        } while(rowNumber % SQUARES_PER_RANK != 0);
        return Collections.unmodifiableList(Arrays.asList(row));
    }

    private Map<String, Integer> createPosToCoordinates() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = 0; i < SQUARES_NUM; i++) {
            positionToCoordinate.put(CHESS_NOTATION.get(i), i);
        }
        return Collections.unmodifiableMap(positionToCoordinate);
    }

    private static List<String> createStandardChessNotation() {
        return Collections.unmodifiableList(Arrays.asList(
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"));
    }

    public static boolean isValidSquareCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < SQUARES_NUM;
    }

    public int getCoordinateAtPos(final String position) {
        return POS_TO_COORDINATES.get(position);
    }

    public String getPosAtCoordinate(final int coordinate) {
        return CHESS_NOTATION.get(coordinate);
    }

    public static boolean isThreatenedBoardImmediate(final Board board) {
        return board.whitePlayer().isInCheck() || board.blackPlayer().isInCheck();
    }

    public static boolean kingThreat(final Move move) {
        final Board board = move.getBoard();
        final MoveShift transition = board.currentPlayer().makeMove(move);
        return transition.getToBoard().currentPlayer().isInCheck();
    }

    public static boolean isKingPawnTrap(final Board board,
                                         final King king,
                                         final int frontTile) {
        final Piece piece = board.getPiece(frontTile);
        return piece != null &&
                piece.getPieceType() == Piece.PieceType.PAWN &&
                piece.getPieceColor() != king.getPieceColor();
    }

    public static int mvvlva(final Move move) {
        final Piece movingPiece = move.getMovedPiece();
        if(move.isAttack()) {
            final Piece attackedPiece = move.getAttackedPiece();
            return (attackedPiece.getPieceValue() - movingPiece.getPieceValue() +  Piece.PieceType.KING.getPieceValue()) * 100;
        }
        return Piece.PieceType.KING.getPieceValue() - movingPiece.getPieceValue();
    }

    public static List<Move> lastNMoves(final Board board, int N) {
        final List<Move> moveHistory = new ArrayList<>();
        Move currentMove = board.getTransitionMove();
        int i = 0;
        while(currentMove != MoveCreation.getInvalidMove() && i < N) {
            moveHistory.add(currentMove);
            currentMove = currentMove.getBoard().getTransitionMove();
            i++;
        }
        return Collections.unmodifiableList(moveHistory);
    }

    public static boolean isEndGame(final Board board) {
        return board.currentPlayer().isInCheckMate() ||
                board.currentPlayer().isInStaleMate();
    }
}
