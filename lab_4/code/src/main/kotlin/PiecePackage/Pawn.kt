package PiecePackage

import BoardPackage.Square

class Pawn(pieceColor: PieceColor) : Piece(pieceColor) {
        init {
            this.pieceName = "pawn"
        }
}