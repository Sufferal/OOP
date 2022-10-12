package GamePackage

import BoardPackage.Board
import PiecePackage.Pawn
import PiecePackage.Piece
import PiecePackage.PieceColor
import PiecePackage.Queen

class Game {
    fun runGame() {
        val board = Board()
        board.createBoard()
        board.printBoard()

        val color = PieceColor.white
        val p1: Pawn = Pawn(color)
        val p2: Piece = Queen(color)
        this.printPiece(p1)
        this.printPiece(p2)
    }

    fun printPiece(piece: Piece) {
        piece.printPieceInfo()
    }
}