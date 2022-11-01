package com.chess.logic.board;

import com.chess.logic.GeneralColor;
import com.chess.logic.pieces.*;
import com.google.common.collect.ImmutableList;

import java.util.*;

public class Board {

    private final List<Square> chessBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    private Board(Builder builder) {
        this.chessBoard = createChessBoard(builder);
        this.whitePieces = findActivePieces(this.chessBoard, GeneralColor.WHITE);
        this.blackPieces = findActivePieces(this.chessBoard, GeneralColor.BLACK);

        final Collection<Move> whiteLegalMoves = findLegalMoves(this.whitePieces);
        final Collection<Move> blackLegalMoves = findLegalMoves(this.blackPieces);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < BoardExtra.NUM_SQUARES; i++) {
            final String squareTxt = this.chessBoard.get(i).toString();
            builder.append(String.format("%3s", squareTxt));
            if((i + 1) % BoardExtra.NUM_SQUARES_PER_ROW == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private Collection<Move> findLegalMoves(final Collection<Piece> generalPieces) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final Piece piece: generalPieces) {
            legalMoves.addAll(piece.searchLegalMoves(this));
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static Collection<Piece> findActivePieces(final List<Square> chessBoard, final GeneralColor color) {
        final List<Piece> activePieces = new ArrayList<>();

        for (final Square square : chessBoard) {
            if(square.isSquareOccupied()) {
                final Piece piece = square.getPiece();
                if(piece.getPieceColor() == color) {
                    activePieces.add(piece);
                }
            }
        }

        return ImmutableList.copyOf(activePieces);
    }

    public Square getSquare(int finalCoordinate) {
        return chessBoard.get(finalCoordinate);
    }

    private static List<Square> createChessBoard(final Builder builder) {
        final Square[] squares = new Square[BoardExtra.NUM_SQUARES];
        for(int i = 0; i < BoardExtra.NUM_SQUARES; i++) {
            squares[i] = Square.createSquare(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(squares);
    }

    public static Board createBoard() {
        final Builder builder = new Builder();

        // Black side
        builder.setPiece(new Rook(0, GeneralColor.BLACK));
        builder.setPiece(new Knight(1, GeneralColor.BLACK));
        builder.setPiece(new Bishop(2, GeneralColor.BLACK));
        builder.setPiece(new Queen(3, GeneralColor.BLACK));
        builder.setPiece(new King(4, GeneralColor.BLACK));
        builder.setPiece(new Bishop(5, GeneralColor.BLACK));
        builder.setPiece(new Knight(6, GeneralColor.BLACK));
        builder.setPiece(new Rook(7, GeneralColor.BLACK));
        // Pawns
        builder.setPiece(new Pawn(8, GeneralColor.BLACK));
        builder.setPiece(new Pawn(9, GeneralColor.BLACK));
        builder.setPiece(new Pawn(10, GeneralColor.BLACK));
        builder.setPiece(new Pawn(11, GeneralColor.BLACK));
        builder.setPiece(new Pawn(12, GeneralColor.BLACK));
        builder.setPiece(new Pawn(13, GeneralColor.BLACK));
        builder.setPiece(new Pawn(14, GeneralColor.BLACK));
        builder.setPiece(new Pawn(15, GeneralColor.BLACK));

        // White side
        // Pawns
        builder.setPiece(new Pawn(48, GeneralColor.WHITE));
        builder.setPiece(new Pawn(49, GeneralColor.WHITE));
        builder.setPiece(new Pawn(50, GeneralColor.WHITE));
        builder.setPiece(new Pawn(51, GeneralColor.WHITE));
        builder.setPiece(new Pawn(52, GeneralColor.WHITE));
        builder.setPiece(new Pawn(53, GeneralColor.WHITE));
        builder.setPiece(new Pawn(54, GeneralColor.WHITE));
        builder.setPiece(new Pawn(55, GeneralColor.WHITE));

//        builder.setPiece(new Rook(56, GeneralColor.WHITE));
        builder.setPiece(new Knight(57, GeneralColor.WHITE));
        builder.setPiece(new Bishop(58, GeneralColor.WHITE));
//        builder.setPiece(new Queen(59, GeneralColor.WHITE));
        builder.setPiece(new King(60, GeneralColor.WHITE));
        builder.setPiece(new Bishop(61, GeneralColor.WHITE));
        builder.setPiece(new Knight(62, GeneralColor.WHITE));
//        builder.setPiece(new Rook(63, GeneralColor.WHITE));

        // 1st to play is white
        builder.setMove(GeneralColor.WHITE);

        return builder.build();
    }

    public static class Builder {

        Map<Integer, Piece> boardConfig;
        GeneralColor nextMovePlayer;

        public Builder() {
            this.boardConfig = new HashMap<>();
        }

        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPieceCoordinate(), piece);
            return this;
        }

        public Builder setMove(final GeneralColor nextMovePlayer) {
            this.nextMovePlayer = nextMovePlayer;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }
}
