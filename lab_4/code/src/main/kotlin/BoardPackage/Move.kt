package BoardPackage

import PiecePackage.Piece
import PlayerPackage.Account
import PlayerPackage.Player

class Move(
    val player: Player,
    var piece: Piece,
    var currentSquare: Square,
    var finalSquare: Square
):Account() {
//    fun printMove() {
//        piece.square = finalSquare
//        println("${piece.pieceColor} ${piece.name} moved from ${currentSquare.location} to ${finalSquare.location}")
//    }

    fun capturePiece(finalSquare: Square) {
        println("$piece.pieceColor $piece captured the piece on $finalSquare")
    }

    fun check() {
        println("Your king is in check!")
    }

    fun checkmate() {
        println("Your king is checkmated!")
    }

    fun stalemate() {
        println("Stalemate!")
    }

    fun promote() {
        println("You promoted a pawn")
    }

    fun castle() {
        println("You castled")
    }

    fun enPassant() {
        println("You captured a pawn en passant")
    }
}