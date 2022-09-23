package ru.mironov.domain

import android.util.Log

class DbTest(private val dao: BaseDao, private val testName: String) {

    private val counter = TimeCounter()

    fun insertBySingleEmptyDBnoConfTest(
        list: List<BaseTestDTO>,
        assertClear: (Int) -> Unit,
        assertAddedCount: (Int) -> Unit
    ) {
        dao.resetTable()
        var count = dao.getRowsCount()
        assertClear.invoke(count)

        counter.start()
        list.forEach {
            dao.insert(it)
        }

        counter.end()

        count = dao.getRowsCount()
        assertAddedCount.invoke(count)

        Log.d(TAG, "$testName.insertTest time;" + counter.calcTimeMillis())

    }

    fun insertAllEmptyDBnoConfTest(
        list: List<BaseTestDTO>,
        assertClear: (Int) -> Unit,
        assertAddedCount: (Int) -> Unit
    ) {
        dao.resetTable()
        var count = dao.getRowsCount()
        assertClear.invoke(count)

        counter.start()

        dao.insertAll(list)

        counter.end()

        count = dao.getRowsCount()
        assertAddedCount.invoke(count)

        Log.d(TAG, "$testName.insertTestAll time;" + counter.calcTimeMillis())

    }

    fun insertAllRawEmptyDBnoConfTest(
        list: List<BaseTestDTO>,
        assertClear: (Int) -> Unit,
        assertAddedCount: (Int) -> Unit
    ) {
        dao.resetTable()
        var count = dao.getRowsCount()
        assertClear.invoke(count)

        counter.start()

        dao.insertAllRawQuery(list)

        counter.end()

        count = dao.getRowsCount()
        assertAddedCount.invoke(count)

        Log.d(TAG, "$testName.insertTestAllRawQuery time;" + counter.calcTimeMillis())

    }

    fun insertAllTransactionTest(
        list: List<BaseTestDTO>,
        assertClear: (Int) -> Unit,
        assertAddedCount: (Int) -> Unit
    ) {
        dao.resetTable()
        var count = dao.getRowsCount()
        assertClear.invoke(count)

        counter.start()

        dao.insertAllTransaction(list)

        counter.end()

        count = dao.getRowsCount()
        assertAddedCount.invoke(count)

        Log.d(TAG, "$testName.insertTestTransaction time;" + counter.calcTimeMillis())

    }

    fun insertMillionTest(
        list: List<BaseTestDTO>,
        assertClear: (Int) -> Unit,
        assertAddedCount: (Int) -> Unit
    ) {
        dao.resetTable()
        var count = dao.getRowsCount()
        assertClear.invoke(count)

        counter.start()

        dao.insertLoop(list)

        counter.end()

        count = dao.getRowsCount()
        assertAddedCount.invoke(count)

        Log.d(TAG, "$testName.insertLoop time;" + counter.calcTimeMillis())

    }

    companion object {
        const val TAG = "Test_tag"
    }
}