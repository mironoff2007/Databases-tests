package ru.mironov.roomdb

import androidx.room.*
import ru.mironov.domain.BaseTestDTO.Companion.ID_FIELD_NAME
import ru.mironov.roomdb.TestObject.Companion.TABLE_NAME

@Dao
interface TestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(videoHistory: TestObject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg obj: TestObject)

    @Query("SELECT * FROM $TABLE_NAME Where $ID_FIELD_NAME Like :id")
    fun getObject(id: Int): TestObject

    @Query("SELECT * FROM $TABLE_NAME")
    fun getObjects(): List<TestObject>

    @Query("DELETE FROM $TABLE_NAME")
    fun resetTable()

    @Query("DELETE FROM sqlite_sequence")
    fun resetCounter()

    @Query("SELECT COUNT(*) FROM $TABLE_NAME")
    fun getRowsCount(): Int

}