class GameDisplay(var game: Game) {
    fun displayGame() {
        println("${game.playerOne.name} vs ${game.playerTwo.name}")
        println("R N B Q K B N R")
        println("P P P P P P P P")

        println("* * * * * * * *")
        println("* * * * * * * *")
        println("* * * * * * * *")
        println("* * * * * * * *")

        println("P P P P P P P P")
        println("R N B Q K B N R")
    }
}