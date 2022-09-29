class Piece(
    val name: String,
    val pieceColor: String,
    var square: Square
) {
    fun printPieceInfo() {
        println("$pieceColor $name on ${square.location} square")
    }
}