class Player(
    var name: String,
    var playerColor: String,
    var winCount: Int,
    var drawCount: Int,
    var lossCount: Int
    ) {

    private fun changePlayerColor(): String {
        if (playerColor == "white") {
            playerColor = "black"
        } else {
            playerColor = "white"
        }
        return playerColor;
    }

    fun addPlayerWin() {
        winCount++
        changePlayerColor()
    }

    fun addPlayerDraw() {
        drawCount++
        changePlayerColor()
    }

    fun addPlayerLoss() {
        lossCount++
        changePlayerColor()
    }

    fun printPlayerInfo() {
        println("$name plays with $playerColor pieces and has $winCount wins, $drawCount draws and $lossCount loses")
    }
}