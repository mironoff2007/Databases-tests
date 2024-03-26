package ru.mironov.roomdb.large

import android.content.Context
import ru.mironov.domain.BaseDao
import ru.mironov.domain.BaseTestDTO


class DaoLargeRoom(context: Context) {

    private val dao = TestLargeDatabase.getDatabase(context).testLargeDao()

    fun insert(obj: TestLargeObject) {
        dao.insert(obj)
    }

    fun insert(list: List<TestLargeObject>) {
        dao.insert(list)
    }

    fun get(): List<TestLargeObject> {
        return dao.getObjects()
    }


    fun resetTable() {
        dao.resetTable()
        dao.resetCounter()
    }


}