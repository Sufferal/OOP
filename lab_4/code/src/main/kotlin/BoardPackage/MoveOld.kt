package BoardPackage

import PiecePackage.OldPiece
import PlayerPackage.Account
import PlayerPackage.Player

class MoveOld(
    val player: Player,
    var oldPiece: OldPiece,
    var currentSquare: Square,
    var finalSquare: Square
):Account() {
//    fun printMove() {
//        piece.square = finalSquare
//        println("${piece.pieceColor} ${piece.name} moved from ${currentSquare.location} to ${finalSquare.location}")
//    }

    fun capturePiece(finalSquare: Square) {
        println("$oldPiece.pieceColor $oldPiece captured the piece on $finalSquare")
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