package ru.mironov.roomdb

import android.content.Context
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import ru.mironov.domain.TimeCounter
import java.util.*


@RunWith(AndroidJUnit4::class)
class DbTest {

        @get:Rule
        var name: TestName = TestName()

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun addAndGetTest() {

        val dao = TestDatabase.getDatabase(appContext).testDao()

        dao.resetTable()
        dao.resetCounter()

        dao.addObject(TestObject(name = "name 1", date = Date().toString(), foreignId = 1))
        dao.addObject(TestObject(name = "name 2", date = Date().toString(), foreignId = 2))

        val list = dao.getObjects()
        assertEquals(list.size, 2)
    }

    @Test
    fun insertTest() {

        val dao = TestDatabase.getDatabase(appContext).testDao()

        val list = TestObject.createMockList(1000)
        dao.resetTable()
        dao.resetCounter()

        val count = dao.getObjects().size

        assertEquals(count, 0)

        val counter = TimeCounter()
        counter.start()
        list.forEach {
            dao.addObject(it)
        }
        counter.end()

        Log.d("Test_tag", "${name.methodName} time - " + counter.calcTimeMillis())

        assert(true)
    }

}