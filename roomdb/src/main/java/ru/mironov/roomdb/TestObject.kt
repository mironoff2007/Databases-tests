package ru.mironov.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.mironov.domain.BaseTestDto
import java.util.*

@Entity
data class TestObject(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    override val name: String,
    override val date: String,
    override val foreignId: Int
): BaseTestDto(name, date, foreignId) {

    companion object {
        const val DB_NAME = "db_room"
        const val TABLE_NAME = "TestObject"

        fun createMockList(size: Int): List<TestObject> {
            val list = mutableListOf<TestObject>()
            repeat(size) {
                list.add(
                    TestObject(
                        id = it.toLong(),
                        name = "name $it",
                        date = Date().toString(),
                        foreignId = it)
                )
            }
            return list
        }
    }
}