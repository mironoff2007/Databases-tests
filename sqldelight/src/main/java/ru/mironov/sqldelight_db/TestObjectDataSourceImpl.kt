package ru.mironov.sqldelight_db


import ru.mironov.domain.BaseDao
import ru.mironov.domain.BaseTestDTO

class TestObjectDataSourceImpl(
    db: TestObjectsDb
): BaseDao {

    private val queries = db.testObjectEntityQueries

    override fun insert(obj: BaseTestDTO) {
        TODO("Not yet implemented")
    }

    override fun insertLoop(list: List<BaseTestDTO>) {
        TODO("Not yet implemented")
    }

    override fun insertAll(list: List<BaseTestDTO>) {
        TODO("Not yet implemented")
    }

    override fun insertAllRawQuery(list: List<BaseTestDTO>) {
        TODO("Not yet implemented")
    }

    override fun insertAllTransaction(list: List<BaseTestDTO>) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<BaseTestDTO> {
        TODO("Not yet implemented")
    }

    override fun resetTable() {
        TODO("Not yet implemented")
    }

    override fun getRowsCount(): Int {
        TODO("Not yet implemented")
    }

    override fun selectBetween(idStart: Int, idEnd: Int): List<BaseTestDTO> {
        TODO("Not yet implemented")
    }


}