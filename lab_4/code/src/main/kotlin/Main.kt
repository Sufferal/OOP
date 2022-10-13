import BoardPackage.*
import GamePackage.Game
import PiecePackage.Pawn
import PiecePackage.PieceColor

fun main() {
    val game1 = Game()
    game1.createGame()
    game1.printBoardDefault()
    game1.runGame()
    game1.showGameHistory()
}