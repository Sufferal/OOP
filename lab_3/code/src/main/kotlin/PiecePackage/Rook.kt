package PiecePackage

import BoardPackage.Square

class Rook(name: String = "rook", pieceColor: String, square: Square) : Piece(name, pieceColor, square) {
    override fun pieceMovement() {
        if(!square.isOnEdges()) {
            this.availableMoves.add("vertical-up")
            this.availableMoves.add("vertical-down")
            this.availableMoves.add("horizontal-right")
            this.availableMoves.add("horizontal-left")
        } else {
            this.availableMoves.add("vertical")
        }
    }
}