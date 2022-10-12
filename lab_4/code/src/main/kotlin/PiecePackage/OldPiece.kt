package PiecePackage

import BoardPackage.Square
import PlayerPackage.Account

open class OldPiece(
    protected val name: String,
    protected val pieceColor: String,
    var square: Square
) : Account() {
    protected var availableMoves: MutableList<String> = arrayListOf<String>()

    open fun show() {
        println("I'm a generic piece")
    }

    protected open fun moveSquareUp() {
        println("Square up")
    }

    protected open fun moveSquareDown() {
        println("Square down")
    }

    protected open fun moveSquareRight() {
        println("Square right")
    }

    protected open fun moveSquareLeft() {
        println("Square left")
    }

    protected open fun pieceMovement(): Unit? {
        return null
    }
}