package BoardPackage

class PositionCreation {
    companion object {
        private val files: Array<File> = File.values()
        fun create(tmpPosition: Position, fileDist: Int, rankDist: Int): Position {
            // a = 0; b = 1; c = 2; d = 3; e = 4; f = 5; g = 6; h = 7
            val tmpFile: Int = tmpPosition.getFile().ordinal
            return Position(files[tmpFile + fileDist], tmpPosition.getRank() + rankDist)
        }
    }
}