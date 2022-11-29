package com.chess.logic.board;

import com.chess.logic.Color;
import com.chess.logic.move.Move;
import com.chess.logic.move.MoveCreation;
import com.chess.logic.pieces.*;
import com.chess.logic.player.BlackPlayer;
import com.chess.logic.player.Player;
import com.chess.logic.player.WhitePlayer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Board {

    private final Map<Integer, Piece> boardConfig;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;
    private final Pawn enPassantPawn;
    private final Move transitionMove;

    private static final Board STANDARD_BOARD = createStandardBoardImpl();
    private Board(final Builder builder) {
        this.boardConfig = Collections.unmodifiableMap(builder.boardConfig);
        this.whitePieces = calculateActivePieces(builder, Color.WHITE);
        this.blackPieces = calculateActivePieces(builder, Color.BLACK);

        this.enPassantPawn = builder.enPassantPawn;

        final Collection<Move> whiteStandardMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardMoves = calculateLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardMoves, blackStandardMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardMoves, blackStandardMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayerByColor(this.whitePlayer, this.blackPlayer);
        this.transitionMove = builder.transitionMove != null ? builder.transitionMove : MoveCreation.getInvalidMove();
    }

    private static String boardVisuals(final Piece piece) {
        if(piece != null) {
            return piece.getPieceColor().isWhite() ?
                    "+" + piece.toString() :
                    "b" + piece.toString();
        }
        return ".";
    }

    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }
    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    public Collection<Piece> getAllPieces() {
        return Stream.concat(this.whitePieces.stream(),
                this.blackPieces.stream()).collect(Collectors.toList());
    }
    public Collection<Move> getAllLegalMoves() {
        return Stream.concat(this.whitePlayer.getLegalMoves().stream(),
                this.blackPlayer.getLegalMoves().stream()).collect(Collectors.toList());
    }

    public WhitePlayer whitePlayer() {
        return this.whitePlayer;
    }
    public BlackPlayer blackPlayer() {
        return this.blackPlayer;
    }
    public Player currentPlayer() {
        return this.currentPlayer;
    }

    public Piece getPiece(final int coordinate) {
        return this.boardConfig.get(coordinate);
    }
    public Pawn getEnPassantPawn() {
        return this.enPassantPawn;
    }
    public Move getTransitionMove() {
        return this.transitionMove;
    }

    public static Board createStandardBoard() {
        return STANDARD_BOARD;
    }

    public static Board createStalematedBoard() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new King(Color.BLACK, 0, false, false));


        builder.setPiece(new Queen(Color.WHITE, 17));
        builder.setPiece(new King(Color.WHITE, 18, false, false));

        //white to move
        builder.setMoveMaker(Color.WHITE);
        //build the board
        return builder.build();
    }
    public static Board createQueenRookCheckmateBoard() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new King(Color.BLACK, 27, false, false));


        builder.setPiece(new Queen(Color.WHITE, 56));
        builder.setPiece(new King(Color.WHITE, 60, false, false));
        builder.setPiece(new Rook(Color.WHITE, 63));

        //white to move
        builder.setMoveMaker(Color.WHITE);
        //build the board
        return builder.build();
    }
    public static Board create2RooksCheckmateBoard() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new King(Color.BLACK, 28, false, false));

        builder.setPiece(new Rook(Color.WHITE, 56));
        builder.setPiece(new King(Color.WHITE, 60, false, false));
        builder.setPiece(new Rook(Color.WHITE, 63));

        //white to move
        builder.setMoveMaker(Color.WHITE);
        //build the board
        return builder.build();
    }
    public static Board createPosition1Board() {
        final Builder builder = new Builder();
        builder.setPiece(new Rook(Color.BLACK, 0));
        builder.setPiece(new Knight(Color.BLACK, 1));
        builder.setPiece(new Bishop(Color.BLACK, 27));
        builder.setPiece(new Queen(Color.BLACK, 19));
        builder.setPiece(new King(Color.BLACK, 6, false, false));
        builder.setPiece(new Bishop(Color.BLACK, 14));
        builder.setPiece(new Rook(Color.BLACK, 3));
        builder.setPiece(new Pawn(Color.BLACK, 8));
        builder.setPiece(new Pawn(Color.BLACK, 10));
        builder.setPiece(new Pawn(Color.BLACK, 12));
        builder.setPiece(new Pawn(Color.BLACK, 13));
        builder.setPiece(new Pawn(Color.BLACK, 22));
        builder.setPiece(new Pawn(Color.BLACK, 15));
        // White Layout
        builder.setPiece(new Pawn(Color.WHITE, 48));
        builder.setPiece(new Pawn(Color.WHITE, 49));
        builder.setPiece(new Pawn(Color.WHITE, 25));
        builder.setPiece(new Pawn(Color.WHITE, 35));
        builder.setPiece(new Pawn(Color.WHITE, 53));
        builder.setPiece(new Pawn(Color.WHITE, 54));
        builder.setPiece(new Pawn(Color.WHITE, 47));
        builder.setPiece(new Rook(Color.WHITE, 58));
        builder.setPiece(new Bishop(Color.WHITE, 52));
        builder.setPiece(new Queen(Color.WHITE, 59));
        builder.setPiece(new King(Color.WHITE, 62, false, false));
        builder.setPiece(new Bishop(Color.WHITE, 44));
        builder.setPiece(new Knight(Color.WHITE, 45));
        builder.setPiece(new Rook(Color.WHITE, 61));

        //white to move
        builder.setMoveMaker(Color.WHITE);
        //build the board
        return builder.build();
    }
    public static Board createPosition2Board() {
        final Builder builder = new Builder();
        builder.setPiece(new Rook(Color.BLACK, 0));
        builder.setPiece(new Knight(Color.BLACK, 17));
        builder.setPiece(new Bishop(Color.BLACK, 2));
        builder.setPiece(new Queen(Color.BLACK, 10));
        builder.setPiece(new King(Color.BLACK, 6, false, false));
        builder.setPiece(new Bishop(Color.BLACK, 14));
        builder.setPiece(new Rook(Color.BLACK, 5));
        builder.setPiece(new Pawn(Color.BLACK, 32));
        builder.setPiece(new Pawn(Color.BLACK, 25));
        builder.setPiece(new Pawn(Color.BLACK, 18));
        builder.setPiece(new Pawn(Color.BLACK, 20));
        builder.setPiece(new Pawn(Color.BLACK, 22));
        builder.setPiece(new Pawn(Color.BLACK, 13));
        builder.setPiece(new Pawn(Color.BLACK, 15));
        // White Layout
        builder.setPiece(new Pawn(Color.WHITE, 40));
        builder.setPiece(new Pawn(Color.WHITE, 49));
        builder.setPiece(new Pawn(Color.WHITE, 53));
        builder.setPiece(new Pawn(Color.WHITE, 54));
        builder.setPiece(new Pawn(Color.WHITE, 46));
        builder.setPiece(new Pawn(Color.WHITE, 44));
        builder.setPiece(new Pawn(Color.WHITE, 35));
        builder.setPiece(new Rook(Color.WHITE, 59));
        builder.setPiece(new Knight(Color.WHITE, 36));
        builder.setPiece(new Bishop(Color.WHITE, 50));
        builder.setPiece(new Queen(Color.WHITE, 52));
        builder.setPiece(new King(Color.WHITE, 58, false, false));
        builder.setPiece(new Knight(Color.WHITE, 45));
        builder.setPiece(new Rook(Color.WHITE, 63));

        //white to move
        builder.setMoveMaker(Color.WHITE);
        //build the board
        return builder.build();
    }

    private static Board createStandardBoardImpl() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Color.BLACK, 0));
        builder.setPiece(new Knight(Color.BLACK, 1));
        builder.setPiece(new Bishop(Color.BLACK, 2));
        builder.setPiece(new Queen(Color.BLACK, 3));
        builder.setPiece(new King(Color.BLACK, 4, true, true));
        builder.setPiece(new Bishop(Color.BLACK, 5));
        builder.setPiece(new Knight(Color.BLACK, 6));
        builder.setPiece(new Rook(Color.BLACK, 7));
        builder.setPiece(new Pawn(Color.BLACK, 8));
        builder.setPiece(new Pawn(Color.BLACK, 9));
        builder.setPiece(new Pawn(Color.BLACK, 10));
        builder.setPiece(new Pawn(Color.BLACK, 11));
        builder.setPiece(new Pawn(Color.BLACK, 12));
        builder.setPiece(new Pawn(Color.BLACK, 13));
        builder.setPiece(new Pawn(Color.BLACK, 14));
        builder.setPiece(new Pawn(Color.BLACK, 15));
        // White Layout
        builder.setPiece(new Pawn(Color.WHITE, 48));
        builder.setPiece(new Pawn(Color.WHITE, 49));
        builder.setPiece(new Pawn(Color.WHITE, 50));
        builder.setPiece(new Pawn(Color.WHITE, 51));
        builder.setPiece(new Pawn(Color.WHITE, 52));
        builder.setPiece(new Pawn(Color.WHITE, 53));
        builder.setPiece(new Pawn(Color.WHITE, 54));
        builder.setPiece(new Pawn(Color.WHITE, 55));
        builder.setPiece(new Rook(Color.WHITE, 56));
        builder.setPiece(new Knight(Color.WHITE, 57));
        builder.setPiece(new Bishop(Color.WHITE, 58));
        builder.setPiece(new Queen(Color.WHITE, 59));
        builder.setPiece(new King(Color.WHITE, 60, true, true));
        builder.setPiece(new Bishop(Color.WHITE, 61));
        builder.setPiece(new Knight(Color.WHITE, 62));
        builder.setPiece(new Rook(Color.WHITE, 63));
        //white to move
        builder.setMoveMaker(Color.WHITE);
        //build the board
        return builder.build();
    }

    private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
        return pieces.stream().flatMap(piece -> piece.calculateLegalMoves(this).stream())
                .collect(Collectors.toList());
    }
    private static Collection<Piece> calculateActivePieces(final Builder builder,
                                                           final Color color) {
        return builder.boardConfig.values().stream()
                .filter(piece -> piece.getPieceColor() == color)
                .collect(Collectors.toList());
    }

    public static class Builder {

        Map<Integer, Piece> boardConfig;
        Color nextMoveMaker;
        Pawn enPassantPawn;
        Move transitionMove;

        public Builder() {
            this.boardConfig = new HashMap<>(32, 1.0f);
        }

        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePos(), piece);
            return this;
        }

        public Builder setMoveMaker(final Color nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Builder setEnPassantPawn(final Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
            return this;
        }

        public Builder setMoveShift(final Move transitionMove) {
            this.transitionMove = transitionMove;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardExtra.SQUARES_NUM; i++) {
            final String squareTxt = boardVisuals(this.boardConfig.get(i));
            builder.append(String.format("%3s", squareTxt));
            if ((i + 1) % 8 == 0) {
                builder.append("  ").append((i+1) / ((i+1)/8) - (((i+1)/8) - 1)).append("\n");
            }
        }
        builder.append("  a  b  c  d  e  f  g  h");
        return builder.toString();
    }
}
