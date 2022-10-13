package GamePackage

import BoardPackage.Board
import BoardPackage.Move
import PiecePackage.Pawn
import PiecePackage.Piece
import PiecePackage.PieceColor
import PiecePackage.Queen

class Game {
    val board = Board()
    private var moves: MutableList<String> = arrayListOf<String>()
    private var currentPlayer: String = "Player 1"

    fun createGame() {
        board.createBoard()
//        board.printBoardCustom()
//        board.printBoard()

    }

    fun runGame() {
        var move: String? = "start"
        while (move != "stop") {
            println("Enter the move $currentPlayer:")
            move = readln()
            if (move != "stop") {
                moves.add(move)
                currentPlayer = if (currentPlayer == "Player 1") {
                    "Player 2"
                } else {
                    "Player 1"
                }
            }
        }
    }

    fun printBoardDefault() {
        println("8 R N B Q K B N R")
        println("7 P P P P P P P P")

        println("6 * * * * * * * *")
        println("5 * * * * * * * *")
        println("4 * * * * * * * *")
        println("3 * * * * * * * *")

        println("2 P P P P P P P P")
        println("1 R N B Q K B N R")
        println("  a b c d e f g h")
    }

    fun showGameHistory() {
        println("Game moves: ")
        for (move in moves) {
            println(move)
        }
    }

    fun getFinalResults() {
        println("Player 1 won with white")
        println("Player 2 lost with black")
    }

    fun printPiece(piece: Move) {
        piece.getLegalMoves(board)
    }
}