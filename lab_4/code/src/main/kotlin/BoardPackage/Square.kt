package BoardPackage

import PiecePackage.Piece
import PlayerPackage.Account

class Square(private val squareColor: SquareColor, private val position: Position) {
    private var isSquareOccupied: Boolean = false
    private lateinit var currentSquarePiece: Piece

    fun getSquareColor(): SquareColor {
        return squareColor
    }

    fun getPosition(): Position {
        return position
    }

    fun getCurrentSquarePiece(): Piece {
        return currentSquarePiece
    }

    fun setCurrentSquarePiece(tmpPiece: Piece)  {
        this.currentSquarePiece = tmpPiece
    }

    fun isSquareOccupied(): Boolean {
        return isSquareOccupied
    }

    fun setSquareOccupied(tmpOccupied: Boolean) {
        this.isSquareOccupied = tmpOccupied
    }



    fun emptySquare() {
        this.isSquareOccupied = false
//        this.currentSquarePiece = null
    }

    fun printSquareInfo() {
        print("Square(SquareColor: $squareColor, Position: ${position.getFile()}${position.getRank()}, isSquareOccupied: $isSquareOccupied)")
    }
}