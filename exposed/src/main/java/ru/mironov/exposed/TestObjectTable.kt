package ru.mironov.exposed

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ru.mironov.domain.BaseDao.Companion.SQL_SELECT_COUNT
import ru.mironov.domain.BaseDao.Companion.TEST_OBJECT_TABLE
import ru.mironov.domain.BaseTestDTO
import ru.mironov.domain.BaseTestDTO.Companion.DATE_FIELD_NAME
import ru.mironov.domain.BaseTestDTO.Companion.RELATION_ID_FIELD_NAME
import ru.mironov.domain.BaseTestDTO.Companion.NAME_FIELD_NAME

object TestObjectTable : Table(TEST_OBJECT_TABLE) {

    private val name = TestObjectTable.varchar(name = NAME_FIELD_NAME, length = 150)
    private val date = TestObjectTable.varchar(name = DATE_FIELD_NAME, length = 50)
    private val foreignId = TestObjectTable.integer(name = RELATION_ID_FIELD_NAME)

    fun initDb(database: Database) {
        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(this@TestObjectTable)
        }
    }

    fun rawQueryWoRes(query: String){
        transaction {
            exec(query) { rs ->

            }
        }
    }

    private fun rawQueryCount(query: String): Int {
        var count = 0
        transaction {
            exec(query) { res ->
                res.next()
                count = res.getInt(1)
            }
        }
        return count
    }

    fun insert(obj: BaseTestDTO) {
        transaction {
            TestObjectTable.insert {
                it[name] = obj.name
                it[date] = obj.date
                it[foreignId] = obj.relationId
            }
        }
    }

    fun inTransaction(method: () -> Unit) {
        transaction {
            method.invoke()
        }
    }

    fun insertAllBatchWoTransaction(testObjects: List<BaseTestDTO>) {
        TestObjectTable.batchInsert(testObjects) {
            this[name] = it.name
            this[date] = it.date
            this[foreignId] = it.relationId
        }
    }

    fun insertAllBatch(testObjects: List<BaseTestDTO>) {
        transaction {
            TestObjectTable.batchInsert(testObjects) {
                this[name] = it.name
                this[date] = it.date
                this[foreignId] = it.relationId
            }
        }
    }

    fun insertAllTransaction(testObjects: List<BaseTestDTO>) {
        transaction {
            testObjects.forEach { obj ->
                TestObjectTable.insert {
                    it[name] = obj.name
                    it[date] = obj.date
                    it[foreignId] = obj.relationId
                }
            }
        }
    }

    fun clear() {
        transaction {
            TestObjectTable.deleteAll()
        }
    }

    fun count(): Int {
        return rawQueryCount(SQL_SELECT_COUNT)
    }

    fun fetchAll(): List<BaseTestDTO> {
        return try {
            transaction {
                TestObjectTable.selectAll().toList()
                    .map {
                        TestObject(
                            name = it[name],
                            date = it[date],
                            relationId = it[foreignId],
                        )//.apply { this.id = it. }
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun selectBetween(start: String, end: String): List<BaseTestDTO> {
        return try {
            transaction {
                val list = TestObjectTable.select() { (name greater start) and (name lessEq end)}
                    .toList()
                    list.map {
                        TestObject(
                            name = it[name],
                            date = it[date],
                            relationId = it[foreignId].toInt(),
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}