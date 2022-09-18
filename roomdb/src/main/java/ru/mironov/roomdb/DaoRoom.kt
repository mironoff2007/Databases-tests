package ru.mironov.roomdb

import android.content.Context
import androidx.sqlite.db.SimpleSQLiteQuery
import ru.mironov.domain.BaseDao
import ru.mironov.domain.BaseTestDTO
import ru.mironov.roomdb.TestObject.Companion.TABLE_NAME

class DaoRoom(context: Context) : BaseDao {

    private val dao = TestDatabase.getDatabase(context).testDao()

    override fun insert(obj: BaseTestDTO) {
        dao.insert(obj as TestObject)
    }

    override fun insertAll(list: List<BaseTestDTO>) {
        val castedList = list as List<TestObject>
        val typedArr = castedList.toTypedArray()
        dao.insertAll(*typedArr)
    }

    override fun insertAllRawQuery(list: List<BaseTestDTO>) {
        val stringBuilder = StringBuilder()
        list.forEach { obj ->
            stringBuilder.apply {
                append(" (")
                append((obj as TestObject).id.toString())
                append(", ")
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
        stringBuilder.deleteCharAt(stringBuilder.lastIndex)

        val insertInto =
            "INSERT INTO $TABLE_NAME (" +
                    "${BaseTestDTO.ID_FIELD_NAME} ," +
                    "${BaseTestDTO.NAME_FIELD_NAME} ," +
                    "${BaseTestDTO.DATE_FIELD_NAME} ," +
                    "${BaseTestDTO.FOREIGN_ID_FIELD_NAME} ) " +
                    "VALUES"
        dao.insertAllBatch(SimpleSQLiteQuery(insertInto + stringBuilder.toString()))
    }

    override fun insertAllTransaction(list: List<BaseTestDTO>) {

    }

    override fun getAll(): List<BaseTestDTO> {
        return dao.getObjects()
    }

    override fun resetTable() {
        dao.resetTable()
        dao.resetCounter()
    }

    override fun getRowsCount(): Int {
        return dao.getRowsCount()
    }
}