package ru.mironov.sqlite

import android.content.Context
import ru.mironov.domain.BaseDao
import ru.mironov.domain.BaseTestDto

class TestDaoSQLite(context: Context): BaseDao {

    private val dao = DBHelper(context)

    override fun insert(obj: BaseTestDto) {
        dao.add(obj as TestObject)
    }

    override fun getAll(): List<BaseTestDto> {
        return dao.getTestObjects()
    }

    override fun resetTable() {
        dao.resetTable()
    }
}