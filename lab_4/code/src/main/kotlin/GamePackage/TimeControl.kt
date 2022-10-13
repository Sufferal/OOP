package GamePackage

abstract class TimeControl {
    protected var time: String? = null
    protected var gameStatus: String? = null
    protected var outOfTime: Boolean = false

    @JvmName("getGameStatus1")
    fun getGameStatus(): String? {
        return gameStatus
    }

    @JvmName("setGameStatus1")
    fun setGameStatus(tmpStatus: String) {
        this.gameStatus = tmpStatus
    }

    @JvmName("getTime1")
    fun getTime(): String? {
        return time
    }

    @JvmName("setTime1")
    fun setTime(tmpTime: String) {
        this.time = tmpTime
    }

    @JvmName("getOutOfTime1")
    fun getOutOfTime(): Boolean {
        return outOfTime
    }

    @JvmName("setTime1")
    fun setTime(tmpOutOfTime: Boolean) {
        this.outOfTime = tmpOutOfTime
    }
}