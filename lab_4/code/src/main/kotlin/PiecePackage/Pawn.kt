package PiecePackage

import BoardPackage.*
import java.util.stream.Collector

class Pawn(pieceColor: PieceColor) : Piece(pieceColor), Move {
    init {
        this.pieceName = "pawn"
    }

    private var isFirstMove: Boolean = true

    override fun makeMove(square: Square) {
        println(this.getPieceName() + ": makeMove()")
    }

    override fun getLegalMoves(board: Board): List<Position> {
        var possibleMoves: MutableList<Position> = emptyList<Position>().toMutableList()
        var tmp: Position = this.getCurrentSquare().getPosition()
        // Move one square
        possibleMoves.add(PositionCreation.create(tmp, 0, 1))

        if(isFirstMove) {
            // Move two squares
            possibleMoves.add(PositionCreation.create(tmp, 0, 2))
            return possibleMoves
        }

        // Capture to the right
        possibleMoves.add(PositionCreation.create(tmp, 1, 1))

        // Capture to the left
        possibleMoves.add(PositionCreation.create(tmp, -1, 1))

        var positionSquareMap: Map<Position, Square> = board.getPositionSquareMap()

        // this ensures that pawn on a and h files do not go over the board
        var legalMoves: List<Position> = possibleMoves.filter(positionSquareMap::containsKey).toList()

        return legalMoves
    }

    override fun getLegalMoves(board: Board, square: Square): List<Position> {
        TODO("Not yet implemented")
    }
}