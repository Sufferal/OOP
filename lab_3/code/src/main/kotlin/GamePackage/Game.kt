package GamePackage

import BoardPackage.Board
import BoardPackage.Move
import PiecePackage.King
import PlayerPackage.Player
import PlayerPackage.TimeControl

class Game(
    var board: Board,
    var playerOne: Player,
    var playerTwo: Player,
    var timePlayerOne: TimeControl,
    var timePlayerTwo: TimeControl
) {
    private var moves: MutableList<String> = arrayListOf<String>()
    private var currentPlayer = playerOne

    fun runGame() {
        var move: String? = "start"
        while (move != "stop") {
            move = readln()
            if (move != "stop") {
                println("Enter the move $currentPlayer: ")
                moves.add(move)
                println(move)
                currentPlayer = if (currentPlayer == playerOne) {
                    playerTwo
                } else {
                    playerOne
                }
            }
        }
    }

    fun showGameHistory() {
        println("Game moves: ")
        for (move in moves) {
            println(move)
        }
    }

    fun getFinalResults() {
        println("${playerTwo.name} won with ${playerTwo.playerColor}")
        println("${playerOne.name} lost with ${playerOne.playerColor}")
    }
}