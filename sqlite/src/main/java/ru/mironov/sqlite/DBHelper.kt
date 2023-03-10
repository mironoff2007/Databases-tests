package ru.mironov.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.mironov.domain.BaseDao
import ru.mironov.domain.BaseDao.Companion.getInsertAllString
import ru.mironov.domain.BaseTestDTO
import ru.mironov.domain.BaseTestDTO.Companion.DATE_FIELD_NAME
import ru.mironov.domain.BaseTestDTO.Companion.RELATION_ID_FIELD_NAME
import ru.mironov.domain.BaseTestDTO.Companion.ID_FIELD_NAME
import ru.mironov.domain.BaseTestDTO.Companion.NAME_FIELD_NAME
import ru.mironov.sqlite.TestObject.Companion.DB_NAME
import ru.mironov.sqlite.TestObject.Companion.TABLE_NAME

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun add(obj: BaseTestDTO) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(ID_FIELD_NAME, (obj as TestObject).id)
            put(NAME_FIELD_NAME, obj.name)
            put(DATE_FIELD_NAME, obj.date)
            put(RELATION_ID_FIELD_NAME, obj.relationId)
        }

        db?.insert(TABLE_NAME, null, values)
    }

    fun insertAll(list: List<BaseTestDTO>) {
        val db = this.writableDatabase
        db.execSQL(SQL_INSERT_INTO_AUTOINCREMENT + getInsertAllString(list))
    }

    fun insertAllWithTransaction(list: List<BaseTestDTO>) {
        val db = this.writableDatabase
        db.beginTransaction()
        list.forEach { add(it) }
        db.setTransactionSuccessful()
        db.endTransaction()
    }

    fun insertAllLoop(list: List<BaseTestDTO>) {
        val db = this.writableDatabase
        db.beginTransaction()
        val insert = fun (subList: List<BaseTestDTO>) {
            val insertString = getInsertAllString(subList)
            db.execSQL(SQL_INSERT_INTO_AUTOINCREMENT + insertString)
        }
        BaseDao.insertLoop(list, insert)
        db.setTransactionSuccessful()
        db.endTransaction()
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
            TestObject(id = id, name = name, date = date, relationId = foreignId)
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
                        relationId = foreignId
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

    fun selectBetween(idStart: String, idEnd: String): List<BaseTestDTO> {
        val list = mutableListOf<TestObject>()
        return try {

            val db = this.readableDatabase
            val sqlStr = "SELECT * FROM $TABLE_NAME WHERE $NAME_FIELD_NAME>'$idStart' AND $NAME_FIELD_NAME<='$idEnd'"
            val cursor = db.rawQuery(sqlStr, null)
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
                        relationId = foreignId
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

    private fun drop() {
        val db = this.writableDatabase
        db.execSQL(SQL_DELETE_ENTRIES)
    }

    private fun create() {
        val db = this.writableDatabase
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    fun resetTable() {
        drop()
        create()
    }

    fun getRowsCount(): Int {
        val db = this.writableDatabase
        val cursor = db.rawQuery(SQL_ROWS_COUNT, null)
        return if (cursor.count > 0) {
            cursor.moveToFirst()
            cursor.getInt("COUNT(*)") ?: 0
        } else 0
    }



    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                    "$ID_FIELD_NAME INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$NAME_FIELD_NAME TEXT," +
                    "$DATE_FIELD_NAME TEXT," +
                    "$RELATION_ID_FIELD_NAME INTEGER)"

        private const val SQL_INSERT_INTO =
            "INSERT INTO $TABLE_NAME (" +
                    "$ID_FIELD_NAME ," +
                    "$NAME_FIELD_NAME ," +
                    "$DATE_FIELD_NAME ," +
                    "$RELATION_ID_FIELD_NAME ) " +
                    "VALUES"

        private const val SQL_INSERT_INTO_AUTOINCREMENT =
            "INSERT INTO $TABLE_NAME (" +
                    "$NAME_FIELD_NAME ," +
                    "$DATE_FIELD_NAME ," +
                    "$RELATION_ID_FIELD_NAME ) " +
                    "VALUES"

        private const val SQL_ROWS_COUNT = "SELECT COUNT(*) FROM $TABLE_NAME "

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}