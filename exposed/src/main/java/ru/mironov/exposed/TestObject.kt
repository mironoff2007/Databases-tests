package ru.mironov.exposed

import ru.mironov.domain.BaseTestDTO
import java.util.*


data class TestObject(
    override val name: String,
    override val date: String,
    override val relationId: Int
): BaseTestDTO(name, date, relationId) {

    var id: Long = 0L

    companion object {

        fun createMockList(size: Int): List<TestObject> {
            val list = mutableListOf<TestObject>()
            repeat(size) {
                val ind = it + 1
                list.add(
                    TestObject(
                        name = "name $ind",
                        date = Date().toString(),
                        relationId = ind)
                )
            }
            return list
        }
    }
}