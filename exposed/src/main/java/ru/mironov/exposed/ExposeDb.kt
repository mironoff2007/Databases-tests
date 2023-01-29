package ru.mironov.exposed

import ru.mironov.domain.BaseDao
import ru.mironov.domain.BaseTestDTO

class ExposeDb: BaseDao {
    override fun insert(obj: BaseTestDTO) {
        TestObjectTable.insert(obj)
    }

    override fun insertLoop(list: List<BaseTestDTO>) {
        TODO("Not yet implemented")
    }

    override fun insertAll(list: List<BaseTestDTO>) {
        TestObjectTable.insertAllBatch(list)
    }

    override fun insertAllRawQuery(list: List<BaseTestDTO>) {
        TODO("Not yet implemented")
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