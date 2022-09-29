class Account(
    var role: String,
    var username: String,
    var email: String,
    var elo: Int
) {
    fun updateAccount(roleUPD: String, usernameUPD: String, emailUPD: String, eloUPD: Int) {
        role = roleUPD
        username = usernameUPD
        email = emailUPD
        elo = eloUPD
    }

    fun printAccountInfo() {
        println("$username has $role role with $elo elo")
    }
}