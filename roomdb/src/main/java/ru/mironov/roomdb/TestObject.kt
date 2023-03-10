package ru.mironov.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.mironov.domain.BaseTestDTO
import java.util.*

@Entity
data class TestObject(
    override val name: String,
    override val date: String,
    override val relationId: Int
): BaseTestDTO(name, date, relationId) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    companion object {
        const val DB_NAME = "db_room"
        const val TABLE_NAME = "TestObject"

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