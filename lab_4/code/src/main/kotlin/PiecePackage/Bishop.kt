package PiecePackage

import BoardPackage.Board
import BoardPackage.Move
import BoardPackage.Position
import BoardPackage.Square

class Bishop(pieceColor: PieceColor) : Piece(pieceColor), Move {
    init {
        this.pieceName = "bishop"
    }

    override fun makeMove(square: Square) {
        println(this.getPieceName() + ": makeMove()")
    }

    override fun getLegalMoves(board: Board): List<Position> {
        println(this.getPieceName() + ": getLegalMoves()")
        return emptyList()
    }

    override fun getLegalMoves(board: Board, square: Square): List<Position> {
        TODO("Not yet implemented")
    }
}