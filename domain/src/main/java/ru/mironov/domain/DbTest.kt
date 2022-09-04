package ru.mironov.domain

import android.util.Log

class DbTest(private val dao: BaseDao, private val testName: String) {

    private val counter = TimeCounter()

    fun insertTest(list: List<BaseTestDto>) {
        dao.resetTable()

        counter.start()
        list.forEach {
            dao.insert(it)
        }

        counter.end()

        Log.d(TAG, "$testName.insertTest avg time;" + counter.calcTimeMillis())

    }

    companion object {
        const val TAG = "Test_tag"
    }
}