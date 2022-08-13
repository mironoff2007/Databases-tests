package ru.mironov.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.mironov.databasestests.BaseTestDto

@Entity
data class TestObject(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    override val name: String,
): BaseTestDto(name) {

    companion object {
        const val ID_FIELD_NAME = "id"
        const val DB_NAME = "TestObject"
    }
}