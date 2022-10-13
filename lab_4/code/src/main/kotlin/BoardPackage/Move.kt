package BoardPackage

interface Move {
    fun makeMove(square: Square)
    fun getLegalMoves(board: Board): List<Position>
    fun getLegalMoves(board: Board, square: Square): List<Position>
}