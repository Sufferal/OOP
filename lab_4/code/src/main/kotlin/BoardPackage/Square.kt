package BoardPackage

import PlayerPackage.Account

class Square(private val squareColor: SquareColor, private val position: Position) {
    private var isSquareOccupied: Boolean = false

    fun getSquareColor(): SquareColor {
        return squareColor
    }

    fun getPosition(): Position {
        return position
    }

    fun emptySquare() {
        isSquareOccupied = false
    }

    fun printSquareInfo() {
        print("Square(SquareColor: $squareColor, Position: ${position.getFile()}${position.getRank()}, isSquareOccupied: $isSquareOccupied)")
    }
}