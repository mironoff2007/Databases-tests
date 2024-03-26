package ru.mironov.roomdb.large

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import ru.mironov.domain.BaseDao
import ru.mironov.domain.BaseTestDTO
import ru.mironov.domain.BaseTestDTO.Companion.ID_FIELD_NAME
import ru.mironov.domain.LargeObject.TABLE_LARGE_NAME
import ru.mironov.roomdb.TestObject.Companion.TABLE_NAME

@Dao
interface TestLargeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(testObject: TestLargeObject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<TestLargeObject>)

    @Query("SELECT * FROM $TABLE_LARGE_NAME Where $ID_FIELD_NAME Like :id")
    fun getObject(id: Int): TestLargeObject


    @Transaction
    @Query("SELECT * FROM $TABLE_LARGE_NAME")
    fun getObjects(): List<TestLargeObject>

    @Query("DELETE FROM $TABLE_LARGE_NAME")
    fun resetTable()

    @Query("DELETE FROM sqlite_sequence WHERE name='$TABLE_LARGE_NAME'")
    fun resetCounter()

    @Query("SELECT COUNT(*) FROM $TABLE_LARGE_NAME")
    fun getRowsCount(): Int

}