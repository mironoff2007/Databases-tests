package ru.mironov.sqldelight_db

import ru.mironov.domain.BaseDao
import ru.mironov.domain.BaseTestDTO
import sqldelightdb.persondb.TestObjectEntity

class DaoSqlite(
    db: TestObjectsDb
): BaseDao {

    private val queries = db.testObjectEntityQueries

    override fun insert(obj: BaseTestDTO) {
        val testObj = obj as TestObject
        queries.insertTestObject(id = obj.id, name = obj.name, date = testObj.date, foreignId = testObj.foreignId.toLong())
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
        return queries.getAllTestObject().executeAsList().map { it.toBaseObject() }
    }

    override fun resetTable() {
        queries.drop()
    }

    override fun getRowsCount(): Int {
        return queries.count().executeAsOne().toInt()
    }

    override fun selectBetween(idStart: Int, idEnd: Int): List<BaseTestDTO> {
        TODO("Not yet implemented")
    }

    fun TestObjectEntity.toBaseObject(): BaseTestDTO {
        val obj = TestObject(
            date = this.date,
            name = this. name,
            foreignId = this.foreignId.toInt()
        )
        obj.id = this.id
        return obj
    }

}