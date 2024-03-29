package ru.mironov.sqlite

import android.content.Context
import ru.mironov.domain.BaseDao
import ru.mironov.domain.BaseTestDTO

class DaoSQLite(context: Context): BaseDao {

    private val dao = DBHelper(context)

    override fun insert(obj: BaseTestDTO) {
        dao.add(obj as TestObject)
    }

    override fun insertLoop(list: List<BaseTestDTO>) {
        dao.insertAllLoop(list)
    }

    override fun insertAll(list: List<BaseTestDTO>) {
       dao.insertAll(list)
    }

    override fun insertAllRawQuery(list: List<BaseTestDTO>) {
        dao.insertAll(list)
    }

    override fun insertAllSingleInTransaction(list: List<BaseTestDTO>) {
        dao.insertAllWithTransaction(list)
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

    override fun selectBetween(idStart: Int, idEnd:Int): List<BaseTestDTO> {
        return dao.selectBetween("name $idStart", "name $idEnd")
    }
}