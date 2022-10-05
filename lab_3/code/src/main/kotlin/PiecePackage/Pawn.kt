package PiecePackage

import BoardPackage.Square

class Pawn(name: String = "pawn", pieceColor: String, square: Square) : Piece(name, pieceColor, square) {
    private fun firstMove(square: Square): Boolean {
        println("first move")
        return true
    }

    private fun hasCaptured(square: Square): Boolean {
        println("capture")
        return true
    }

    private fun hasPromoted(square: Square): Boolean {
        println("promote")
        return true
    }
    override fun pieceMovement() {
        if(firstMove(square)) {
            this.availableMoves.add("up2")
        } else if(hasCaptured(square)) {
            this.availableMoves.add("up-left")
        } else if(hasPromoted(square)) {
            this.availableMoves.add("promote")
        }
    }
}