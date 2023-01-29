package ru.mironov.domain

interface BaseDao {

    fun insert(obj: BaseTestDTO)

    fun insertLoop(list: List<BaseTestDTO>)

    fun insertAll(list: List<BaseTestDTO>)

    fun insertAllRawQuery(list: List<BaseTestDTO>)

    fun insertAllSingleInTransaction(list: List<BaseTestDTO>)

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

        fun insertLoop(list: List<BaseTestDTO>, insert: (List<BaseTestDTO>) -> Unit) {
            val repeatCount = list.size / Constants.ADD_COUNT + 1
            repeat(repeatCount) {
                val startPos = it * Constants.ADD_COUNT
                var endPos = startPos + Constants.ADD_COUNT
                if (endPos > list.size - 1) endPos = list.size
                val subList = list.subList(startPos, endPos)

                if (subList.isNotEmpty()) {
                    insert.invoke(subList)
                }
            }
        }
    }

}