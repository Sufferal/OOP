class Board {
    val ChessBoard : Array<Array<Square>> = Array(8){ Array(8){  Square("a", "1") } }

    fun resetBoard() {
        println("Start new game")
    }
}