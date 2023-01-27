package ru.mironov.roomdb

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import ru.mironov.domain.BaseTestDTO
import ru.mironov.domain.BaseTestDTO.Companion.ID_FIELD_NAME
import ru.mironov.domain.Constants
import ru.mironov.roomdb.TestObject.Companion.TABLE_NAME

@Dao
interface TestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(videoHistory: TestObject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg obj: TestObject)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTransaction(vararg obj: TestObject)

    @RawQuery
    fun insertAllBatch(query: SupportSQLiteQuery): Boolean

    @Transaction
    fun inInsertLoop(list: List<BaseTestDTO>) {
        val repeatCount = list.size / Constants.ADD_COUNT + 1
        repeat(repeatCount) {
            val startPos = it * Constants.ADD_COUNT
            var endPos = startPos + Constants.ADD_COUNT
            if (endPos > list.size - 1) endPos = list.size
            val subList = list.subList(startPos, endPos)
            if (subList.isNotEmpty()) {
                val castedList = subList as List<TestObject>
                val typedArr = castedList.toTypedArray()
                insertAll(*typedArr)
            }
        }
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
                append(obj.foreignId)
                append("),")
            }
        }
        var lastInd = stringBuilder.lastIndex
        if (lastInd > 0) stringBuilder.deleteCharAt(lastInd)

        val insertInto = "INSERT INTO $TABLE_NAME (" +
                "${BaseTestDTO.NAME_FIELD_NAME} ," +
                "${BaseTestDTO.DATE_FIELD_NAME} ," +
                "${BaseTestDTO.FOREIGN_ID_FIELD_NAME} ) " +
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