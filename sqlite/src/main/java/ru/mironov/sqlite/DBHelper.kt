package ru.mironov.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.mironov.domain.BaseTestDto.Companion.DATE_FIELD_NAME
import ru.mironov.domain.BaseTestDto.Companion.FOREIGN_ID_FIELD_NAME
import ru.mironov.domain.BaseTestDto.Companion.NAME_FIELD_NAME
import ru.mironov.domain.BaseTestDto.Companion.ID_FIELD_NAME
import ru.mironov.sqlite.TestObject.Companion.DB_NAME
import ru.mironov.sqlite.TestObject.Companion.TABLE_NAME

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insert(obj: TestObject) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(ID_FIELD_NAME, obj.id)
            put(NAME_FIELD_NAME, obj.name)
            put(DATE_FIELD_NAME, obj.date)
            put(FOREIGN_ID_FIELD_NAME, obj.foreignId)
        }

        db?.insert(TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    fun getTestObjectById(id: Int): TestObject? {
        return try {

            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $ID_FIELD_NAME = $id", null)
            cursor.moveToFirst()
            val id = cursor.getLong(ID_FIELD_NAME) ?: 0L
            val name = cursor.getString(NAME_FIELD_NAME) ?: ""
            val date = cursor.getString(DATE_FIELD_NAME) ?: ""
            val foreignId = cursor.getInt(DATE_FIELD_NAME) ?: 0

            cursor.close()
            TestObject(id = id, name = name, date = date, foreignId = foreignId)
        } catch (e: Exception) {
            null
        }
    }

    @SuppressLint("Range")
    fun getTestObjects(): List<TestObject> {
        val list = mutableListOf<TestObject>()
        return try {

            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
            var i = 0
            if (cursor.count > 0) {
                while (cursor.count != i) {

                    cursor.moveToFirst()
                    val id = cursor.getLong(ID_FIELD_NAME) ?: 0L
                    val name = cursor.getString(NAME_FIELD_NAME) ?: ""
                    val date = cursor.getString(DATE_FIELD_NAME) ?: ""
                    val foreignId = cursor.getInt(DATE_FIELD_NAME) ?: 0

                    val obj = TestObject(
                        id = id,
                        name = name,
                        date = date,
                        foreignId = foreignId
                    )

                    list.add(obj)
                    i++
                }
            }
            cursor.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    fun drop() {
        val db = this.writableDatabase
        db.rawQuery(SQL_DELETE_ENTRIES, null)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                    "$ID_FIELD_NAME LONG PRIMARY KEY," +
                    "$NAME_FIELD_NAME TEXT," +
                    "$DATE_FIELD_NAME TEXT," +
                    "$FOREIGN_ID_FIELD_NAME INTEGER)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}