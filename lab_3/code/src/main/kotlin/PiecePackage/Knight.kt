package PiecePackage

import BoardPackage.Square

class Knight(name: String = "queen", pieceColor: String, square: Square) : Piece(name, pieceColor, square) {
    override fun pieceMovement() {
        if(!square.isOnEdges()) {
            this.availableMoves.add("L-forward-right")
            this.availableMoves.add("L-forward-left")
            this.availableMoves.add("L-backward-right")
            this.availableMoves.add("L-backward-left")
            this.availableMoves.add("L-side-right")
            this.availableMoves.add("L-side-left")
        } else {
            this.availableMoves.add("diagonal")
        }
    }
}