package ru.mironov.sqldelight_db


import ru.mironov.domain.BaseTestDTO
import java.util.*


data class TestObject(
    override val name: String,
    override val date: String,
    override val foreignId: Int
): BaseTestDTO(name, date, foreignId) {

    var id: Long = 0L

    companion object {

        fun createMockList(size: Int): List<TestObject> {
            val list = mutableListOf<TestObject>()
            repeat(size) {
                val ind = it + 1
                val obj = TestObject(
                    name = "name $ind",
                    date = Date().toString(),
                    foreignId = ind
                )
                obj.id = ind.toLong()
                list.add(obj)
            }
            return list
        }
    }
}