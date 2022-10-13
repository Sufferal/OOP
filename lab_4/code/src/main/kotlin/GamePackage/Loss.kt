package GamePackage

class Loss: TimeControl() {
    init {
        this.gameStatus = "loss"
    }

    fun loseByOutOfTime() {
        this.outOfTime = true
        println("lose by outOfTime")
    }
}