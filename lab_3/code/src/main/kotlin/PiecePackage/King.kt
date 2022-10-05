package PiecePackage

import BoardPackage.Square

class King(name: String = "king", pieceColor: String, square: Square) : Piece(name, pieceColor, square) {
    override fun pieceMovement() {
        if(!square.isOnEdges()) {
            this.availableMoves.add("up")
            this.availableMoves.add("down")
            this.availableMoves.add("left")
            this.availableMoves.add("right")
            this.availableMoves.add("up-left")
            this.availableMoves.add("up-right")
            this.availableMoves.add("down-left")
            this.availableMoves.add("down-right")
        } else {
            // TODO: conditions to check
            this.availableMoves.add("e4")
        }
    }
}