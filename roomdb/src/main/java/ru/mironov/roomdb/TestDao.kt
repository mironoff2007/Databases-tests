package ru.mironov.roomdb

import androidx.room.*
import ru.mironov.roomdb.TestObject.Companion.DB_NAME
import ru.mironov.roomdb.TestObject.Companion.ID_FIELD_NAME

@Dao
interface TestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addObject(videoHistory: TestObject)

    @Query("SELECT * FROM $DB_NAME Where $ID_FIELD_NAME Like :id")
    fun getObject(id: Int): TestObject

    @Query("SELECT * FROM $DB_NAME")
    fun getObjects(): List<TestObject>

    @Query("DELETE FROM $DB_NAME")
    fun resetTable( )

    @Query("DELETE FROM sqlite_sequence")
    fun resetCounter()

}