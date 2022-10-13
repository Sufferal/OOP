package PiecePackage

import BoardPackage.File
import BoardPackage.Position

class PieceCreation {
    companion object {
        fun getPieces(): MutableMap<Position, Piece> {
            var allPieces: MutableMap<Position, Piece> = HashMap()

            // Kings
            allPieces[Position(File.e, 1)] = King(PieceColor.white)
            allPieces[Position(File.e, 8)] = King(PieceColor.black)

            // Queens
            allPieces[Position(File.d, 1)] = Queen(PieceColor.white)
            allPieces[Position(File.d, 8)] = Queen(PieceColor.black)

            // Bishops
            allPieces[Position(File.c, 1)] = Bishop(PieceColor.white)
            allPieces[Position(File.f, 1)] = Bishop(PieceColor.white)
            allPieces[Position(File.c, 8)] = Bishop(PieceColor.black)
            allPieces[Position(File.f, 8)] = Bishop(PieceColor.black)

            // Knights
            allPieces[Position(File.b, 1)] = Bishop(PieceColor.white)
            allPieces[Position(File.g, 1)] = Bishop(PieceColor.white)
            allPieces[Position(File.b, 8)] = Bishop(PieceColor.black)
            allPieces[Position(File.g, 8)] = Bishop(PieceColor.black)

            // Rooks
            allPieces[Position(File.a, 1)] = Bishop(PieceColor.white)
            allPieces[Position(File.h, 1)] = Bishop(PieceColor.white)
            allPieces[Position(File.a, 8)] = Bishop(PieceColor.black)
            allPieces[Position(File.h, 8)] = Bishop(PieceColor.black)

            // Pawns
            for (file in File.values()) {
                allPieces[Position(file, 2)] = Pawn(PieceColor.white)
                allPieces[Position(file, 7)] = Pawn(PieceColor.black)
            }

            return allPieces
        }
    }

}