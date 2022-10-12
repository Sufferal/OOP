package BoardPackage

class Position(private val file: File, private val rank: Int) {
    fun getFile(): File {
        return file;
    }

    fun getRank(): Int {
        return rank;
    }

//    override fun hashCode(): Int {
//        return super.hashCode()
//    }
}