fun main() {
    // Square
    println("===== SQUARE =====")
    val sq1 = Square("e", "4")
    println("Most beginners learn the 1." + sq1.location + " opening")
    val sq2 = Square("a", "1")
    sq2.squareColor = "black"
    sq2.printSquareInfo()
    println("===== SQUARE =====")

    // Piece
    println("\n===== PIECE =====")
    val Pawn1 = Piece("pawn", "white", Square("e", "4"))
    val Pawn2 = Piece("pawn", "black", Square("c", "5"))
    val King = Piece("king", "white", Square("e", "2"))
    val Queen = Piece("queen", "white", Square("d", "8"))
    val Bishop = Piece("bishop", "white", Square("f", "7"))
    val Knight = Piece("knight", "white", Square("g", "4"))
    val Rook = Piece("rook", "white", Square("h", "8"))

    val allPieces = arrayOf(Pawn1, Pawn2, King, Queen, Bishop, Knight, Rook)
    for (piece in allPieces) {
        piece.printPieceInfo()
    }
    println("===== PIECE =====")

    // Player
    println("\n===== PLAYER =====")
    val MagnusCarlsen = Player("Magnus", "white", 9, 3, 0)
    val HansNiemann = Player("Niemann", "black", 1, 2, 5)
    HansNiemann.printPlayerInfo()
    MagnusCarlsen.printPlayerInfo()
    MagnusCarlsen.addPlayerLoss()
    HansNiemann.addPlayerWin()
    MagnusCarlsen.printPlayerInfo()
    HansNiemann.printPlayerInfo()
    println("===== PLAYER =====")

    // Move
    println("\n===== MOVE =====")
    val Move1 = Move(MagnusCarlsen, Pawn1, Square("e", "2"), Square("e", "4"))
    Move1.printMove()
    val Move2 = Move(HansNiemann, Pawn2, Square("c", "7"), Square("c", "5"))
    Move2.printMove()
    println("===== MOVE =====")

    // Computer
    println("\n===== COMPUTER =====")
    val Stockfish = Computer("white", 100, 0, 0)
    Stockfish.suggestMove()
    println("===== COMPUTER =====")

    // TimeControl
    println("\n===== TIME CONTROL =====")
    val time1 = TimeControl(MagnusCarlsen, "1 hour")
    time1.showTimeRemaining()
    val time2 = TimeControl(HansNiemann, "10 hours")
    time2.showTimeRemaining()
    println("===== TIME CONTROL =====")

    // Account
    println("\n===== ACCOUNT =====")
    val admin = Account("admin", "boss", "boss@gmail.com", 3000)
    val pleb = Account("pleb", "noob123", "noob@mail.ru", 600)
    val mod = Account("mod", "powertrip", "arrogant@yahoo.com", 1500)

    admin.printAccountInfo()
    pleb.printAccountInfo()
    mod.printAccountInfo()
    println("===== ACCOUNT =====")

    // Baord
    println("\n===== BOARD =====")
    val simpleBoard = Board()
    simpleBoard.resetBoard()
    println("===== BOARD =====")

    // Game
    println("\n===== GAME =====")
    val game1 = Game(simpleBoard, MagnusCarlsen, HansNiemann, time1, time2)
    game1.getFinalResults()
    println("===== GAME =====")

    // Game Display
    println("\n===== GAME DISPLAY =====")
    val game1Display = GameDisplay(game1)
    game1Display.displayGame()
    println("===== GAME DISPLAY =====")
}