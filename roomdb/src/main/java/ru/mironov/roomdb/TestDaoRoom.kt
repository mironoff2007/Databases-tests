package ru.mironov.roomdb

import android.content.Context
import ru.mironov.domain.BaseDao
import ru.mironov.domain.BaseTestDto

class TestDaoRoom(context: Context): BaseDao {

    private val dao = TestDatabase.getDatabase(context).testDao()

    override fun insert(obj: BaseTestDto) {
        dao.add(obj as TestObject)
    }

    override fun getAll(): List<BaseTestDto> {
        return dao.getObjects()
    }

    override fun resetTable() {
        dao.resetTable()
        dao.resetCounter()
    }
}