package ru.mironov.roomdb

import android.content.Context
import ru.mironov.domain.BaseDao
import ru.mironov.domain.BaseTestDTO


class DaoRoom(context: Context) : BaseDao {

    private val dao = TestDatabase.getDatabase(context).testDao()

    override fun insert(obj: BaseTestDTO) {
        dao.insert(obj as TestObject)
    }

    override fun insertLoop(list: List<BaseTestDTO>) {
        dao.inInsertLoop(list)
    }

    override fun insertAll(list: List<BaseTestDTO>) {
        val castedList = list as List<TestObject>
        val typedArr = castedList.toTypedArray()
        dao.insertAll(*typedArr)
    }

    override fun insertAllRawQuery(list: List<BaseTestDTO>) {
        dao.insertAllBatch(dao.insertQuery(list))
    }

    override fun insertAllSingleInTransaction(list: List<BaseTestDTO>) {
        dao.insertAllTransaction(list)
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

    override fun selectBetween(idStart: Int, idEnd: Int): List<BaseTestDTO> {
        return dao.selectBetween("name $idStart", "name $idEnd")
    }
}