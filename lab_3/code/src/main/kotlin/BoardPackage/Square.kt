package BoardPackage

import PlayerPackage.Account

class Square(private var file: String, private var rank: String): Account() {
    var location: String? = "$file$rank"
    private var squareColor: String? = null
    private var isOccupied: Boolean = false

    fun changeIsOccupied() {
        isOccupied = !isOccupied
    }

    fun isOnEdges(): Boolean {
        println("Square is on edges")
        return false
    }

    fun printSquareInfo() {
        println("BoardPackage.Square $location is $squareColor")
    }
}