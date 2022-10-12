package BoardPackage

import PlayerPackage.Account

class Board: Account() {
    private val boardSquares = 8

    var chessBoard : Array<Array<Square>> = Array(boardSquares){ Array(boardSquares){ Square(SquareColor.white, Position(File.a, 1)) }}
    fun createBoard() {
        for ((index, value) in chessBoard.withIndex()) {
            var col: Int = 0
            var currentColor = if(index % 2 == 0) {
                SquareColor.white
            } else {
                SquareColor.black
            }

            for(tmpFile in File.values()) {
                var tmpSquare = Square(currentColor, Position(tmpFile, boardSquares - index))
                chessBoard[index][col] = tmpSquare
                currentColor = if(currentColor == SquareColor.white) SquareColor.black else SquareColor.white
                col++
            }
        }
    }

    fun printBoard() {
        for (row in chessBoard) {
            for (sq in row) {
                sq.printSquareInfo()
            }
            println()
        }
    }

    fun resetBoard() {
        chessBoard = Array(boardSquares){ Array(boardSquares){ Square(SquareColor.white, Position(File.a, 1)) }}
    }
}