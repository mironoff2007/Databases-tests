package ru.mironov.sqlite

import android.content.Context
import ru.mironov.domain.BaseDao
import ru.mironov.domain.BaseTestDTO

class TestDaoSQLite(context: Context): BaseDao {

    private val dao = DBHelper(context)

    override fun insert(obj: BaseTestDTO) {
        dao.add(obj as TestObject)
    }

    override fun insertAll(list: List<BaseTestDTO>) {
       dao.insertAll(list)
    }

    override fun getAll(): List<BaseTestDTO> {
        return dao.getTestObjects()
    }

    override fun resetTable() {
        dao.resetTable()
    }

    override fun getRowsCount(): Int {
       return dao.getRowsCount()
    }
}