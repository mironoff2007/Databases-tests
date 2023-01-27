package ru.mironov.domain

interface BaseDao {

    fun insert(obj: BaseTestDTO)

    fun insertLoop(list: List<BaseTestDTO>)

    fun insertAll(list: List<BaseTestDTO>)

    fun insertAllRawQuery(list: List<BaseTestDTO>)

    fun insertAllTransaction(list: List<BaseTestDTO>)

    fun getAll(): List<BaseTestDTO>

    fun resetTable()

    fun getRowsCount(): Int

    fun selectBetween(idStart: Int, idEnd:Int): List<BaseTestDTO>


    companion object {
        fun getInsertAllString(list: List<BaseTestDTO>): String {
            val stringBuilder = StringBuilder()
            list.forEach { obj ->
                stringBuilder.apply {
                    append(" (")
                    append("'")
                    append(obj.name)
                    append("'")
                    append(", ")
                    append("'")
                    append(obj.date)
                    append("'")
                    append(", ")
                    append(obj.foreignId)
                    append("),")
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.lastIndex)
            return stringBuilder.toString()
        }
    }

}