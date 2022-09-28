class Square(val file: String, val rank: String) {
    var location: String? = "$file$rank"
    var color: String? = null
    var isOccupied: Boolean = false

    fun printLocation() {
        println(location)
    }

    fun printColor() {
        println(color)
    }

    fun printInfo() {
        println("Square $location is $color")
    }
}