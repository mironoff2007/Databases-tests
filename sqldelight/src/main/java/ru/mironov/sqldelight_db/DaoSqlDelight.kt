package ru.mironov.sqldelight_db

import com.squareup.sqldelight.db.SqlDriver
import ru.mironov.domain.BaseDao
import ru.mironov.domain.BaseTestDTO
import ru.mironov.domain.Constants
import sqldelightdb.persondb.TestObjectEntity

class DaoSqlDelight(
    private val db: TestObjectsDb,
    private val driver: SqlDriver
): BaseDao {

    private val queries = db.testObjectEntityQueries

    override fun insert(obj: BaseTestDTO) {
        val testObj = obj as TestObject
        queries.insertTestObject(id = obj.id, name = obj.name, date = testObj.date, foreignId = testObj.foreignId.toLong())
    }

    override fun insertLoop(list: List<BaseTestDTO>) {
        db.transaction {
            val repeatCount = list.size / Constants.ADD_COUNT + 1
            repeat(repeatCount) {
                val startPos = it * Constants.ADD_COUNT
                var endPos = startPos + Constants.ADD_COUNT
                if (endPos > list.size - 1) endPos = list.size
                val subList = list.subList(startPos, endPos)

                if (subList.isNotEmpty()) {
                    insertAll(subList)
                }
            }
        }
    }

    override fun insertAll(list: List<BaseTestDTO>) {
        driver.execute(sql = SQL_INSERT_INTO_AUTOINCREMENT + BaseDao.getInsertAllString(list), parameters = 0, binders = null, identifier = null)
    }


    override fun insertAllRawQuery(list: List<BaseTestDTO>) {
        insertAll(list)
    }

    override fun insertAllTransaction(list: List<BaseTestDTO>) {
        db.transaction {
            insertAll(list)
        }
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

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE testObjectEntity (" +
                    "${BaseTestDTO.ID_FIELD_NAME} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${BaseTestDTO.NAME_FIELD_NAME} TEXT," +
                    "${BaseTestDTO.DATE_FIELD_NAME} TEXT," +
                    "${BaseTestDTO.FOREIGN_ID_FIELD_NAME} INTEGER)"

        private const val SQL_INSERT_INTO =
            "INSERT INTO testObjectEntity (" +
                    "${BaseTestDTO.ID_FIELD_NAME} ," +
                    "${BaseTestDTO.NAME_FIELD_NAME} ," +
                    "${BaseTestDTO.DATE_FIELD_NAME} ," +
                    "${BaseTestDTO.FOREIGN_ID_FIELD_NAME} ) " +
                    "VALUES"

        private const val SQL_INSERT_INTO_AUTOINCREMENT =
            "INSERT INTO testObjectEntity (" +
                    "${BaseTestDTO.NAME_FIELD_NAME} ," +
                    "${BaseTestDTO.DATE_FIELD_NAME} ," +
                    "${BaseTestDTO.FOREIGN_ID_FIELD_NAME} ) " +
                    "VALUES"

    }

}