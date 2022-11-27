package com.chess.logic.board;

import com.chess.logic.Color;
import com.chess.logic.board.Move.MoveCreation;
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
    private Board(final BoardCreation boardCreation) {
        this.boardConfig = Collections.unmodifiableMap(boardCreation.boardConfig);
        this.whitePieces = calculateActivePieces(boardCreation, Color.WHITE);
        this.blackPieces = calculateActivePieces(boardCreation, Color.BLACK);

        this.enPassantPawn = boardCreation.enPassantPawn;

        final Collection<Move> whiteStandardMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardMoves = calculateLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardMoves, blackStandardMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardMoves, blackStandardMoves);
        this.currentPlayer = boardCreation.nextMoveMaker.choosePlayerByColor(this.whitePlayer, this.blackPlayer);
        this.transitionMove = boardCreation.transitionMove != null ? boardCreation.transitionMove : MoveCreation.getInvalidMove();
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
        final BoardCreation boardCreation = new BoardCreation();
        // Black Layout
        boardCreation.setPiece(new King(Color.BLACK, 0, false, false));


        boardCreation.setPiece(new Queen(Color.WHITE, 17));
        boardCreation.setPiece(new King(Color.WHITE, 18, false, false));

        //white to move
        boardCreation.setMoveMaker(Color.WHITE);
        //build the board
        return boardCreation.create();
    }
    public static Board createQueenRookCheckmateBoard() {
        final BoardCreation boardCreation = new BoardCreation();
        // Black Layout
        boardCreation.setPiece(new King(Color.BLACK, 27, false, false));


        boardCreation.setPiece(new Queen(Color.WHITE, 56));
        boardCreation.setPiece(new King(Color.WHITE, 60, false, false));
        boardCreation.setPiece(new Rook(Color.WHITE, 63));

        //white to move
        boardCreation.setMoveMaker(Color.WHITE);
        //build the board
        return boardCreation.create();
    }
    public static Board create2RooksCheckmateBoard() {
        final BoardCreation boardCreation = new BoardCreation();
        // Black Layout
        boardCreation.setPiece(new King(Color.BLACK, 28, false, false));

        boardCreation.setPiece(new Rook(Color.WHITE, 56));
        boardCreation.setPiece(new King(Color.WHITE, 60, false, false));
        boardCreation.setPiece(new Rook(Color.WHITE, 63));

        //white to move
        boardCreation.setMoveMaker(Color.WHITE);
        //build the board
        return boardCreation.create();
    }
    public static Board createPosition1Board() {
        final BoardCreation boardCreation = new BoardCreation();
        boardCreation.setPiece(new Rook(Color.BLACK, 0));
        boardCreation.setPiece(new Knight(Color.BLACK, 1));
        boardCreation.setPiece(new Bishop(Color.BLACK, 27));
        boardCreation.setPiece(new Queen(Color.BLACK, 19));
        boardCreation.setPiece(new King(Color.BLACK, 6, false, false));
        boardCreation.setPiece(new Bishop(Color.BLACK, 14));
        boardCreation.setPiece(new Rook(Color.BLACK, 3));
        boardCreation.setPiece(new Pawn(Color.BLACK, 8));
        boardCreation.setPiece(new Pawn(Color.BLACK, 10));
        boardCreation.setPiece(new Pawn(Color.BLACK, 12));
        boardCreation.setPiece(new Pawn(Color.BLACK, 13));
        boardCreation.setPiece(new Pawn(Color.BLACK, 22));
        boardCreation.setPiece(new Pawn(Color.BLACK, 15));
        // White Layout
        boardCreation.setPiece(new Pawn(Color.WHITE, 48));
        boardCreation.setPiece(new Pawn(Color.WHITE, 49));
        boardCreation.setPiece(new Pawn(Color.WHITE, 25));
        boardCreation.setPiece(new Pawn(Color.WHITE, 35));
        boardCreation.setPiece(new Pawn(Color.WHITE, 53));
        boardCreation.setPiece(new Pawn(Color.WHITE, 54));
        boardCreation.setPiece(new Pawn(Color.WHITE, 47));
        boardCreation.setPiece(new Rook(Color.WHITE, 58));
        boardCreation.setPiece(new Bishop(Color.WHITE, 52));
        boardCreation.setPiece(new Queen(Color.WHITE, 59));
        boardCreation.setPiece(new King(Color.WHITE, 62, false, false));
        boardCreation.setPiece(new Bishop(Color.WHITE, 44));
        boardCreation.setPiece(new Knight(Color.WHITE, 45));
        boardCreation.setPiece(new Rook(Color.WHITE, 61));

        //white to move
        boardCreation.setMoveMaker(Color.WHITE);
        //build the board
        return boardCreation.create();
    }
    public static Board createPosition2Board() {
        final BoardCreation boardCreation = new BoardCreation();
        boardCreation.setPiece(new Rook(Color.BLACK, 0));
        boardCreation.setPiece(new Knight(Color.BLACK, 17));
        boardCreation.setPiece(new Bishop(Color.BLACK, 2));
        boardCreation.setPiece(new Queen(Color.BLACK, 10));
        boardCreation.setPiece(new King(Color.BLACK, 6, false, false));
        boardCreation.setPiece(new Bishop(Color.BLACK, 14));
        boardCreation.setPiece(new Rook(Color.BLACK, 5));
        boardCreation.setPiece(new Pawn(Color.BLACK, 32));
        boardCreation.setPiece(new Pawn(Color.BLACK, 25));
        boardCreation.setPiece(new Pawn(Color.BLACK, 18));
        boardCreation.setPiece(new Pawn(Color.BLACK, 20));
        boardCreation.setPiece(new Pawn(Color.BLACK, 22));
        boardCreation.setPiece(new Pawn(Color.BLACK, 13));
        boardCreation.setPiece(new Pawn(Color.BLACK, 15));
        // White Layout
        boardCreation.setPiece(new Pawn(Color.WHITE, 40));
        boardCreation.setPiece(new Pawn(Color.WHITE, 49));
        boardCreation.setPiece(new Pawn(Color.WHITE, 53));
        boardCreation.setPiece(new Pawn(Color.WHITE, 54));
        boardCreation.setPiece(new Pawn(Color.WHITE, 46));
        boardCreation.setPiece(new Pawn(Color.WHITE, 44));
        boardCreation.setPiece(new Pawn(Color.WHITE, 35));
        boardCreation.setPiece(new Rook(Color.WHITE, 59));
        boardCreation.setPiece(new Knight(Color.WHITE, 36));
        boardCreation.setPiece(new Bishop(Color.WHITE, 50));
        boardCreation.setPiece(new Queen(Color.WHITE, 52));
        boardCreation.setPiece(new King(Color.WHITE, 58, false, false));
        boardCreation.setPiece(new Knight(Color.WHITE, 45));
        boardCreation.setPiece(new Rook(Color.WHITE, 63));

        //white to move
        boardCreation.setMoveMaker(Color.WHITE);
        //build the board
        return boardCreation.create();
    }

    private static Board createStandardBoardImpl() {
        final BoardCreation boardCreation = new BoardCreation();
        // Black Layout
        boardCreation.setPiece(new Rook(Color.BLACK, 0));
        boardCreation.setPiece(new Knight(Color.BLACK, 1));
        boardCreation.setPiece(new Bishop(Color.BLACK, 2));
        boardCreation.setPiece(new Queen(Color.BLACK, 3));
        boardCreation.setPiece(new King(Color.BLACK, 4, true, true));
        boardCreation.setPiece(new Bishop(Color.BLACK, 5));
        boardCreation.setPiece(new Knight(Color.BLACK, 6));
        boardCreation.setPiece(new Rook(Color.BLACK, 7));
        boardCreation.setPiece(new Pawn(Color.BLACK, 8));
        boardCreation.setPiece(new Pawn(Color.BLACK, 9));
        boardCreation.setPiece(new Pawn(Color.BLACK, 10));
        boardCreation.setPiece(new Pawn(Color.BLACK, 11));
        boardCreation.setPiece(new Pawn(Color.BLACK, 12));
        boardCreation.setPiece(new Pawn(Color.BLACK, 13));
        boardCreation.setPiece(new Pawn(Color.BLACK, 14));
        boardCreation.setPiece(new Pawn(Color.BLACK, 15));
        // White Layout
        boardCreation.setPiece(new Pawn(Color.WHITE, 48));
        boardCreation.setPiece(new Pawn(Color.WHITE, 49));
        boardCreation.setPiece(new Pawn(Color.WHITE, 50));
        boardCreation.setPiece(new Pawn(Color.WHITE, 51));
        boardCreation.setPiece(new Pawn(Color.WHITE, 52));
        boardCreation.setPiece(new Pawn(Color.WHITE, 53));
        boardCreation.setPiece(new Pawn(Color.WHITE, 54));
        boardCreation.setPiece(new Pawn(Color.WHITE, 55));
        boardCreation.setPiece(new Rook(Color.WHITE, 56));
        boardCreation.setPiece(new Knight(Color.WHITE, 57));
        boardCreation.setPiece(new Bishop(Color.WHITE, 58));
        boardCreation.setPiece(new Queen(Color.WHITE, 59));
        boardCreation.setPiece(new King(Color.WHITE, 60, true, true));
        boardCreation.setPiece(new Bishop(Color.WHITE, 61));
        boardCreation.setPiece(new Knight(Color.WHITE, 62));
        boardCreation.setPiece(new Rook(Color.WHITE, 63));
        //white to move
        boardCreation.setMoveMaker(Color.WHITE);
        //build the board
        return boardCreation.create();
    }

    private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
        return pieces.stream().flatMap(piece -> piece.calculateLegalMoves(this).stream())
                .collect(Collectors.toList());
    }
    private static Collection<Piece> calculateActivePieces(final BoardCreation boardCreation,
                                                           final Color color) {
        return boardCreation.boardConfig.values().stream()
                .filter(piece -> piece.getPieceColor() == color)
                .collect(Collectors.toList());
    }

    public static class BoardCreation {

        Map<Integer, Piece> boardConfig;
        Color nextMoveMaker;
        Pawn enPassantPawn;
        Move transitionMove;

        public BoardCreation() {
            this.boardConfig = new HashMap<>(32, 1.0f);
        }

        public BoardCreation setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePos(), piece);
            return this;
        }

        public BoardCreation setMoveMaker(final Color nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public BoardCreation setEnPassantPawn(final Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
            return this;
        }

        public BoardCreation setMoveShift(final Move transitionMove) {
            this.transitionMove = transitionMove;
            return this;
        }

        public Board create() {
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
