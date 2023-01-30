package ru.mironov.exposed

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import ru.mironov.domain.BaseDao.Companion.SQL_SELECT_COUNT
import ru.mironov.domain.BaseDao.Companion.TEST_OBJECT_TABLE
import ru.mironov.domain.BaseTestDTO

object TestObjectTable : Table(TEST_OBJECT_TABLE) {

    private val name = TestObjectTable.varchar(name = "name", length = 150)
    private val date = TestObjectTable.varchar(name = "date", length = 50)
    private var foreignId = TestObjectTable.integer(name = "foreignId")

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
                it[foreignId] = obj.foreignId
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
            this[foreignId] = it.foreignId
        }
    }

    fun insertAllBatch(testObjects: List<BaseTestDTO>) {
        transaction {
            TestObjectTable.batchInsert(testObjects) {
                this[name] = it.name
                this[date] = it.date
                this[foreignId] = it.foreignId
            }
        }
    }

    fun insertAllTransaction(testObjects: List<BaseTestDTO>) {
        transaction {
            testObjects.forEach { obj ->
                TestObjectTable.insert {
                    it[name] = obj.name
                    it[date] = obj.date
                    it[foreignId] = obj.foreignId
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
                            foreignId = it[foreignId],
                        )//.apply { this.id = it. }
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}