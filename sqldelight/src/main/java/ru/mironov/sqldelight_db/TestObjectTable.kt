package ru.mironov.sqldelight_db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object TestObjectsTable : Table() {
    private val name = TestObjectsTable.varchar(name = "name", length = 150)
    private val foreignId = TestObjectsTable.foreignKey(name = "foreignId" )
    private val id = TestObjectsTable.long("id").autoIncrement()
    private var date = TestObjectsTable.varchar(name = "date", length = 20)
    override val primaryKey = PrimaryKey(id, name = "pr_Id")


    fun insertObject(testObject: TestObject) {
        transaction {
            TestObjectsTable.insert {
                it[name] = testObject.name
                it[date] = testObject.date
            }
        }
    }

    fun fetchAll(): List<TestObject> {
        return try {
            transaction {
                TestObjectsTable.selectAll().toList()
                    .map {
                        TestObject(
                            name = it[name],
                            date = it[date],
                            relationId = 0,
                        ).apply { id = it[TestObjectsTable.id] }
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
