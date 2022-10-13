package GamePackage

class Draw: TimeControl() {
    init {
        this.gameStatus = "draw"
    }

    fun loseByInsufficientMaterial() {
        println("lose by loseByInsufficientMaterial")
    }
}