package PiecePackage

import BoardPackage.Square

class Queen(name: String = "queen", pieceColor: String, square: Square) : Piece(name, pieceColor, square) {
    override fun pieceMovement() {
        if(!square.isOnEdges()) {
            this.availableMoves.add("diagonal-up")
            this.availableMoves.add("diagonal-down")
            this.availableMoves.add("vertical-up")
            this.availableMoves.add("vertical-down")
            this.availableMoves.add("horizontal-right")
            this.availableMoves.add("horizontal-left")
        } else {
            this.availableMoves.add("vertical")
        }
    }
}