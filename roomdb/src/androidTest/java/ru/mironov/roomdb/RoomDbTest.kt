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
import ru.mironov.domain.Constants
import ru.mironov.domain.Constants.ADD_COUNT
import ru.mironov.domain.TimeCounter
import java.util.*


@RunWith(AndroidJUnit4::class)
class RoomDbTest {

    @get:Rule
    var name: TestName = TestName()

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private val dao = TestDatabase.getDatabase(appContext).testDao()

    @Test
    fun addAndGetTest() {

        dao.resetTable()
        dao.resetCounter()

        dao.add(TestObject(name = "name 1", date = Date().toString(), foreignId = 1))
        dao.add(TestObject(name = "name 2", date = Date().toString(), foreignId = 2))

        val list = dao.getObjects()
        assertEquals(list.size, 2)
    }

    @Test
    fun insertTest() {

        dao.resetTable()
        dao.resetCounter()

        val list = TestObject.createMockList(ADD_COUNT)

        val objects = dao.getObjects()

        assertEquals(objects.size, 0)

        val counter = TimeCounter()

        counter.start()
        repeat(Constants.REPEAT_COUNT) {

            list.forEach {
                dao.add(it)
            }
            counter.next()
            counter.pause()

            dao.resetTable()
            dao.resetCounter()

            counter.resume()
        }

        Log.d("Test_tag", "${this.javaClass.name}.${name.methodName} avg time - " + counter.getAvg())
        Log.d("Test_tag", "${this.javaClass.name}.${name.methodName} worst time - " + counter.getWorst())

        assertEquals(list.size, ADD_COUNT)
    }

}