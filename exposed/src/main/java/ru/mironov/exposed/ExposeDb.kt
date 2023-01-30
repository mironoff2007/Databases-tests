package ru.mironov.exposed

import android.content.Context
import ru.mironov.domain.BaseDao
import ru.mironov.domain.BaseDao.Companion.SQL_INSERT_INTO
import ru.mironov.domain.BaseTestDTO

class ExposeDb(context: Context): BaseDao {

    init {
        val db = context.initDatabase()
        TestObjectTable.initDb(db)
    }

    override fun insert(obj: BaseTestDTO) {
        TestObjectTable.insert(obj)
    }

    override fun insertLoop(list: List<BaseTestDTO>) {
        val method = fun() {
            val insert = fun(subList: List<BaseTestDTO>) {
                insertAllRawQuery(subList)
            }
            BaseDao.insertLoop(list, insert)
        }
        TestObjectTable.inTransaction(method)
    }

    override fun insertAll(list: List<BaseTestDTO>) {
        TestObjectTable.insertAllBatch(list)
    }

    override fun insertAllRawQuery(list: List<BaseTestDTO>) {
        val query = SQL_INSERT_INTO+ BaseDao.getInsertAllString(list)
        TestObjectTable.rawQueryWoRes(query)
    }

    override fun insertAllSingleInTransaction(list: List<BaseTestDTO>) {
        TestObjectTable.insertAllTransaction(list)
    }

    override fun getAll(): List<BaseTestDTO> {
        return TestObjectTable.fetchAll()
    }

    override fun resetTable() {
      TestObjectTable.clear()
    }

    override fun getRowsCount(): Int {
       return TestObjectTable.count()
    }

    override fun selectBetween(idStart: Int, idEnd: Int): List<BaseTestDTO> {
        TODO("Not yet implemented")
    }
}