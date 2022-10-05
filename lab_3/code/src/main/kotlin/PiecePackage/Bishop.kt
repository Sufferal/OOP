package PiecePackage

import BoardPackage.Square

class Bishop(name: String = "bishop", pieceColor: String, square: Square) : Piece(name, pieceColor, square) {
    override fun pieceMovement() {
        if(!square.isOnEdges()) {
            this.availableMoves.add("diagonal-up")
            this.availableMoves.add("diagonal-down")
        } else {
            this.availableMoves.add("diagonal")
        }
    }
}