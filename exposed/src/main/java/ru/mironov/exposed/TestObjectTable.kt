package ru.mironov.exposed

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ru.mironov.domain.BaseTestDTO
import java.util.*

object TestObjectTable : Table() {

    private val id: Column<EntityID<UUID>> = uuid("id")
        .autoGenerate()
        .entityId()
    override val primaryKey by lazy { super.primaryKey ?: PrimaryKey(id) }
    private val name = TestObjectTable.varchar(name = "name", length = 150)
    private val date = TestObjectTable.varchar(name = "date", length = 50)
    private var foreignId = TestObjectTable.integer(name = "foreignId")

    fun insert(testObject: BaseTestDTO) {

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
        return fetchAll().size
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
                        ).apply { this.id = id }
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}