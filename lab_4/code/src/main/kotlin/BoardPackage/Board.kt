package BoardPackage

import PiecePackage.Piece
import PiecePackage.PieceColor
import PiecePackage.PieceCreation
import PlayerPackage.Account

class Board: Account() {
    private val boardSquares = 8
    private lateinit var positionSquareMap: Map<Position, Square>

    var chessBoard : Array<Array<Square>> = Array(boardSquares){ Array(boardSquares){ Square(SquareColor.white, Position(File.a, 1)) }}
    fun createBoard() {
        positionSquareMap = HashMap()
        var pieces: Map<Position, Piece> = PieceCreation.getPieces()
        for ((index, value) in chessBoard.withIndex()) {
            var col: Int = 0
            var currentColor = if(index % 2 == 0) {
                SquareColor.white
            } else {
                SquareColor.black
            }

            for(tmpFile in File.values()) {
                var tmpSquare = Square(currentColor, Position(tmpFile, boardSquares - index))
                if (pieces.containsKey(tmpSquare.getPosition())) {
                    var tmpPiece: Piece? = pieces.get(tmpSquare.getPosition())
                    if (tmpPiece != null) {
                        tmpSquare.setCurrentSquarePiece(tmpPiece)
                    }
                    tmpSquare.setSquareOccupied(true)
                    if (tmpPiece != null) {
                        tmpPiece.setCurrentSquare(tmpSquare)
                    }
                }
                (positionSquareMap as HashMap<Position, Square>)[tmpSquare.getPosition()] = tmpSquare
                chessBoard[index][col] = tmpSquare
                currentColor = if(currentColor == SquareColor.white) SquareColor.black else SquareColor.white
                col++
            }
        }
    }

    fun getPositionSquareMap():Map<Position, Square> {
        return positionSquareMap
    }

    fun printBoard() {
        for (row in chessBoard) {
            for (sq in row) {
                sq.printSquareInfo()
            }
            println()
        }
    }

    fun printBoardCustom() {
        for ((i, row) in chessBoard.withIndex()) {
            print("${boardSquares - i} ")
            for ((j, col) in row.withIndex()) {
                if(chessBoard[i][j].isSquareOccupied()) {
                    var piece: Piece = chessBoard[i][j].getCurrentSquarePiece()
                    print(piece.getPieceName().first() + " ")
                } else {
                    // Empty square
                    print("* ")
                }
            }
            println()
        }

        print("  ")
        for (file in File.values()) {
            print("${file.name} ")
        }
        println()
    }

    fun resetBoard() {
        chessBoard = Array(boardSquares){ Array(boardSquares){ Square(SquareColor.white, Position(File.a, 1)) }}
    }
}