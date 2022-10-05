package BoardPackage

class Square(private var file: String, private var rank: String) {
    var location: String? = "$file$rank"
    private var squareColor: String? = null
    private var isOccupied: Boolean = false

    fun changeIsOccupied() {
        isOccupied = !isOccupied
    }

    fun printSquareInfo() {
        println("BoardPackage.Square $location is $squareColor")
    }
}