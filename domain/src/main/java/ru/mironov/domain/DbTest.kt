package ru.mironov.domain

import android.util.Log

class DbTest(private val dao: BaseDao, private val testName: String) {

    private val counter = TimeCounter()

    fun insertTest(
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

    fun insertAllTest(
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

    fun add(list: List<BaseTestDTO>){
        val maxSize=999
        if(list.size>maxSize){
            val listCount = list.size/maxSize
            repeat(listCount){
                val subList = list.subList(it*maxSize, (it+1)*maxSize-1)
                dao.insertAll(subList
                )
            }
        }
        else dao.insertAll(list)
    }

    companion object {
        const val TAG = "Test_tag"
    }
}