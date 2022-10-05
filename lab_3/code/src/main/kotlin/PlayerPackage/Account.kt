package PlayerPackage

open class Account(
    protected var username: String = "Magnus",
    protected var elo: Int = 3000
) {
    protected fun updateAccount(usernameUPD: String, eloUPD: Int) {
        username = usernameUPD
        elo = eloUPD
    }

    fun printAccountInfo() {
        println("$username is at $elo elo")
    }
}