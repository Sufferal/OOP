package GamePackage

import GamePackage.Game

class GameDisplay(var game: Game) {
    fun displayGame() {
        println("${game.playerOne.name} vs ${game.playerTwo.name}")
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