package ru.mironov.sqlite

import ru.mironov.domain.BaseTestDTO
import java.util.*


data class TestObject(
    val id: Long = 0L,
    override val name: String,
    override val date: String,
    override val relationId: Int
): BaseTestDTO(name, date, relationId) {

    companion object {
        const val DB_NAME = "db_sqlite"
        const val TABLE_NAME = "TestObject"

        fun createMockList(size: Int): MutableList<TestObject> {
            val list = mutableListOf<TestObject>()
            repeat(size) {
                val ind = it + 1
                list.add(
                    TestObject(
                        id = ind.toLong(),
                        name = "name $ind",
                        date = Date().toString(),
                        relationId = ind)
                )
            }
            return list
        }
    }

}