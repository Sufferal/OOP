package PiecePackage

import BoardPackage.Square

class Queen(pieceColor: PieceColor) : Piece(pieceColor) {
    init {
        this.pieceName = "queen"
    }
}