class Square(var file: String, var rank: String) {
    var location: String? = "$file$rank"
    var squareColor: String? = null
    var isOccupied: Boolean = false

    fun changeIsOccupied() {
        isOccupied = !isOccupied
    }

    fun printSquareInfo() {
        println("Square $location is $squareColor")
    }
}