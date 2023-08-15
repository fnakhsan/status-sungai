package uin.suka.status.sungai.core.utils

enum class SeasonType(val value: String) {
    SEASON1("Kemarau"),
    SEASON2("Hujan");

    companion object {
        fun getSeasonById(id: Int) = when (id) {
            1 -> SEASON1.value
            2 -> SEASON2.value
            else -> SEASON1.value
        }
    }
}