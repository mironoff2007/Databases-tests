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

    fun selectBetweenTest(
        list: List<BaseTestDTO>,
        assertPopulated: (Int) -> Unit,
        assertAddedCount: (Int) -> Unit,
        idStart: Int,
        idEnd: Int
    ) {
        var count = dao.getRowsCount()
        if (count != Constants.ADD_MILLION) {
            dao.resetTable()
            dao.insertLoop(list)
        }
        count = dao.getRowsCount()

        assertPopulated.invoke(count)

        counter.start()
        val result = dao.selectBetween(idStart,idEnd)
        counter.end()

        assertAddedCount.invoke(result.size)

        Log.d(TAG, "$testName.selectBetween time;" + counter.calcTimeMillis())

    }

    companion object {
        const val TAG = "Test_tag"
    }
}