package ru.mironov.roomdb

import android.content.Context
import ru.mironov.domain.BaseDao
import ru.mironov.domain.BaseTestDTO

class TestDaoRoom(context: Context): BaseDao {

    private val dao = TestDatabase.getDatabase(context).testDao()

    override fun insert(obj: BaseTestDTO) {
        dao.insert(obj as TestObject)
    }

    override fun insertAll(list: List<BaseTestDTO>) {
        val castedList = list as List<TestObject>
        val typedArr = castedList.toTypedArray()
        dao.insertAll(*typedArr)
    }

    override fun getAll(): List<BaseTestDTO> {
        return dao.getObjects()
    }

    override fun resetTable() {
        dao.resetTable()
        dao.resetCounter()
    }

    override fun getRowsCount(): Int {
       return dao.getRowsCount()
    }
}