import BoardPackage.Board
import BoardPackage.Move
import PiecePackage.Piece
import BoardPackage.Square
import GamePackage.Game
import GamePackage.GameDisplay
import PiecePackage.Pawn
import PlayerPackage.Account
import PlayerPackage.Computer
import PlayerPackage.Player
import PlayerPackage.TimeControl

fun main() {
    // Player
    println("\n===== PLAYER =====")
    val magnusCarlsen = Player("Magnus", "white")
    magnusCarlsen.printPlayerInfo()
    val hansNiemann = Player("Niemann", "black")
    hansNiemann.printPlayerInfo()
    println("===== PLAYER =====")

    // TimeControl
    println("\n===== TIME CONTROL =====")
    val time1 = TimeControl(magnusCarlsen, "1 hour")
    time1.showTimeRemaining()
    val time2 = TimeControl(hansNiemann, "10 hours")
    time2.showTimeRemaining()
    println("===== TIME CONTROL =====")

    // Board
    println("\n===== BOARD =====")
    val simpleBoard = Board()
    simpleBoard.resetBoard()
    println("===== BOARD =====")

    // Game
    println("\n===== GAME =====")
    val game1 = Game(simpleBoard, magnusCarlsen, hansNiemann, time1, time2)
    game1.runGame()
    game1.showGameHistory()
    println("===== GAME =====")
}