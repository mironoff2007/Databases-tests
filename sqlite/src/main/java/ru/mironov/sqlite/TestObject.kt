package ru.mironov.sqlite

import ru.mironov.domain.BaseTestDto


data class TestObject(
    val id: Long = 0L,
    override val name: String,
    override val date: String,
    override val foreignId: Int
): BaseTestDto(name, date, foreignId) {

    companion object {
        const val DB_NAME = "db_sqlite"
        const val TABLE_NAME = "TestObject"
    }

}