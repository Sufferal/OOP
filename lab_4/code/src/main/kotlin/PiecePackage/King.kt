package PiecePackage

import BoardPackage.Square

class King(pieceColor: PieceColor) : Piece(pieceColor) {
    init {
        this.pieceName = "king"
    }
}