package PiecePackage

import BoardPackage.Square

open class Piece(
    protected val name: String,
    protected val pieceColor: String,
    var square: Square
) {
    fun printPieceInfo() {
        println("$pieceColor $name on ${square.location} square")
    }

    protected open fun pieceMovement(): Unit? {
        return null
    }
}