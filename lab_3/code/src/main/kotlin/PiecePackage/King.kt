package PiecePackage

import BoardPackage.Square

class King(name: String, pieceColor: String, square: Square) : Piece(name, pieceColor, square) {
    override fun pieceMovement() {
        square
    }
}