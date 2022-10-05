package PlayerPackage

import PlayerPackage.Player

class TimeControl(val player: Player, var time: String) {
    fun tickTime() {
        println("Time is ticking right now")
    }
    fun addTime(timeAdded: String) {
        println("You added time")
    }

    fun removeTime(timeRemoved: String) {
        println("You removed time")
    }
    fun showTimeRemaining() {
        println("${player.name} has $time remaining")
    }
}