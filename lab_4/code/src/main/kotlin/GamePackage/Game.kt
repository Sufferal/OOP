package GamePackage

import BoardPackage.Board
import BoardPackage.Move
import PiecePackage.Pawn
import PiecePackage.Piece
import PiecePackage.PieceColor
import PiecePackage.Queen

class Game {
    val board = Board()
    fun runGame() {
        board.createBoard()
//        board.printBoardCustom()
//        board.printBoard()
    }

    fun printPiece(piece: Move) {
        piece.getLegalMoves(board)
    }
}