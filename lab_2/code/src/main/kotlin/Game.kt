class Game(
    var board: Board,
    var playerOne: Player,
    var playerTwo: Player,
    var timePlayerOne: TimeControl,
    var timePlayerTwo: TimeControl
) {
    fun getFinalResults() {
        println("${playerTwo.name} won with ${playerTwo.playerColor}")
        println("${playerOne.name} lost with ${playerOne.playerColor}")
    }
}