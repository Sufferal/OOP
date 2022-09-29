class Computer(var computerColor: String,
               var winCount: Int,
               var drawCount: Int,
               var lossCount: Int) {
    fun suggestMove() {
        println("You should play this move")
    }
    private fun changeComputerColor(): String {
        if (computerColor == "white") {
            computerColor = "black"
        } else {
            computerColor = "white"
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
        println("Computer plays with $computerColor pieces and has $winCount wins, $drawCount draws and $lossCount loses")
    }
}