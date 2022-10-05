package BoardPackage

import PlayerPackage.Account

class Board: Account() {
    private val board_size = 8

    val ChessBoard : Array<Array<Square>> = Array(board_size){ Array(board_size){ Square("a", "1") }}



    fun resetBoard() {
        println("Start new game")
    }
}