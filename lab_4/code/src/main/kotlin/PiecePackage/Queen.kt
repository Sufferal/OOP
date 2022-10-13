package PiecePackage

import BoardPackage.Board
import BoardPackage.Move
import BoardPackage.Position
import BoardPackage.Square
import java.util.Collections

class Queen(pieceColor: PieceColor) : Piece(pieceColor), Move {
    init {
        this.pieceName = "queen"
    }

    private lateinit var rook: Move
    private lateinit var bishop: Move

    constructor(pieceColor: PieceColor, rook: Move, bishop: Move) : this(pieceColor) {
        this.rook = rook
        this.bishop = bishop
    }

    override fun makeMove(square: Square) {
        val prevSquare: Square = this.getCurrentSquare()
        this.setCurrentSquare(square)
        prevSquare.emptySquare()
    }

    override fun getLegalMoves(board: Board): List<Position> {
        val possibleMoves: MutableList<Position> = Collections.emptyList()
        possibleMoves.addAll(bishop.getLegalMoves(board, this.getCurrentSquare()))
        possibleMoves.addAll(rook.getLegalMoves(board, this.getCurrentSquare()))
        return possibleMoves
    }

    override fun getLegalMoves(board: Board, square: Square): List<Position> {
        TODO("Not yet implemented")
    }
}