package PiecePackage

import BoardPackage.File
import BoardPackage.Position
import BoardPackage.Square
import BoardPackage.SquareColor

abstract class Piece(protected val pieceColor: PieceColor) {
    protected var currentSquare: Square = Square(SquareColor.black, Position(File.a, 1))
    protected lateinit var pieceName: String

    @JvmName("getPieceName1")
    fun getPieceName(): String {
        return pieceName
    }

     @JvmName("getPieceColor1")
     fun getPieceColor(): PieceColor {
        return pieceColor
    }

    @JvmName("getCurrentSquare1")
    fun getCurrentSquare(): Square {
        return currentSquare
    }

    @JvmName("setCurrentSquare1")
    fun setCurrentSquare(tmpSquare: Square) {
        this.currentSquare = tmpSquare
    }

    fun printPieceInfo() {
        println("Piece(pieceName: $pieceName, pieceColor: $pieceColor, currentSquare: ${currentSquare.getPosition().getFile()}${currentSquare.getPosition().getRank()})")
    }
}