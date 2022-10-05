package PlayerPackage

class Computer(
    private var computerColor: String,
    private var winCount: Int,
    private var drawCount: Int,
    private var lossCount: Int, name: String, playerColor: String
) : Player(name, playerColor) {
    fun suggestMove() {
        println("You should play this move")
    }
    private fun changeComputerColor(): String {
        computerColor = if (computerColor == "white") {
            "black"
        } else {
            "white"
        }
        return computerColor;
    }

    fun addComputerWin() {
        winCount++
        changeComputerColor()
    }

    fun addComputerDraw() {
        drawCount++
        changeComputerColor()
    }

    fun addComputerLoss() {
        lossCount++
        changeComputerColor()
    }

    fun printComputerInfo() {
        println("PlayerPackage.Computer plays with $computerColor pieces and has $winCount wins, $drawCount draws and $lossCount loses")
    }
}