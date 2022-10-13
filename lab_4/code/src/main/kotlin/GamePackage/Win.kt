package GamePackage

import BoardPackage.Board
import BoardPackage.Move
import BoardPackage.Position
import BoardPackage.Square

class Win: TimeControl() {
    init {
        this.gameStatus = "win"
    }

    fun winByOpponentTimeLoss() {
        println("win by winByOpponentTimeLoss")
    }
}