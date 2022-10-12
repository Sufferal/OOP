package PlayerPackage

open class Player(
    var name: String,
    var playerColor: String,
    private var winCount: Int = 0,
    private var drawCount: Int = 0,
    private var lossCount: Int = 0
): Account() {

    protected fun changePlayerColor(): String {
        playerColor = if (playerColor == "white") {
            "black"
        } else {
            "white"
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