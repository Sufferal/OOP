package GamePackage

import BoardPackage.Board
import GamePackage.Game
import PlayerPackage.Account
import PlayerPackage.Player
import PlayerPackage.TimeControl

class GameDisplay(board: Board, playerOne: Player, playerTwo: Player, timePlayerOne: TimeControl,
                  timePlayerTwo: TimeControl
): Game(board, playerOne, playerTwo, timePlayerOne, timePlayerTwo) {
    fun displayGame() {
        println("${playerOne.name} vs ${playerTwo.name}")
        println("BR BN BB BQ BK BB BN BR")
        println("BP BP BP BP BP BP BP BP")

        println("* * * * * * * *")
        println("* * * * * * * *")
        println("* * * * * * * *")
        println("* * * * * * * *")

        println("WP WP WP WP WP WP WP WP")
        println("WR WN WB WQ WK WB WN WR")
    }
}