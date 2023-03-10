package ru.mironov.roomdb

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import ru.mironov.domain.BaseDao
import ru.mironov.domain.BaseTestDTO
import ru.mironov.domain.BaseTestDTO.Companion.ID_FIELD_NAME
import ru.mironov.roomdb.TestObject.Companion.TABLE_NAME

@Dao
interface TestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(testObject: TestObject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg obj: TestObject)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTransaction(list: List<BaseTestDTO>) {
        list.forEach { insertAllBatch(insertQuery(listOf(it))) }
    }

    @RawQuery
    fun insertAllBatch(query: SupportSQLiteQuery): Boolean

    @Transaction
    fun inInsertLoop(list: List<BaseTestDTO>) {
        val insert = fun (subList: List<BaseTestDTO>) {
            insertAllBatch(insertQuery(subList))
        }
        BaseDao.insertLoop(list, insert)
    }

    fun insertQuery(list: List<BaseTestDTO>): SimpleSQLiteQuery {
        val stringBuilder = StringBuilder()
        list.forEach { obj ->
            stringBuilder.apply {
                append(" (")
                append("'")
                append(obj.name)
                append("'")
                append(", ")
                append("'")
                append(obj.date)
                append("'")
                append(", ")
                append(obj.relationId)
                append("),")
            }
        }
        var lastInd = stringBuilder.lastIndex
        if (lastInd > 0) stringBuilder.deleteCharAt(lastInd)

        val insertInto = "INSERT INTO $TABLE_NAME (" +
                "${BaseTestDTO.NAME_FIELD_NAME} ," +
                "${BaseTestDTO.DATE_FIELD_NAME} ," +
                "${BaseTestDTO.RELATION_ID_FIELD_NAME} ) " +
                "VALUES"
        return SimpleSQLiteQuery(insertInto + stringBuilder.toString())
    }


    @Query("SELECT * FROM $TABLE_NAME Where $ID_FIELD_NAME Like :id")
    fun getObject(id: Int): TestObject

    @Query("SELECT * FROM $TABLE_NAME")
    fun getObjects(): List<TestObject>

    @Query("DELETE FROM $TABLE_NAME")
    fun resetTable()

    @Query("DELETE FROM sqlite_sequence WHERE name='$TABLE_NAME'")
    fun resetCounter()

    @Query("SELECT COUNT(*) FROM $TABLE_NAME")
    fun getRowsCount(): Int

    @Transaction
    @Query("SELECT * FROM $TABLE_NAME WHERE name>:idStart AND name<=:idEnd")
    fun selectBetween(idStart: String, idEnd: String): List<TestObject>

}