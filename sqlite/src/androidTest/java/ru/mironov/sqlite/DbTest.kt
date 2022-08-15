package ru.mironov.sqlite

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

        val dbHelper = DBHelper(appContext)

        dbHelper.drop()

        dbHelper.insert(
            TestObject(
                id = 1,
                name = "name 1",
                date = Date().toString(),
                foreignId = 1
            )
        )

        dbHelper.insert(
            TestObject(
                id = 2,
                name = "name 2",
                date = Date().toString(),
                foreignId = 2
            )
        )

        val objects = dbHelper.getTestObjects()

        assertEquals(objects.size, 2)
    }

    @Test
    fun insertTest() {

        val dbHelper = DBHelper(appContext)

        dbHelper.drop()

        val list = TestObject.createMockList(1000)

        val counter = TimeCounter()
        counter.start()
        list.forEach {
            dbHelper.insert(it)
        }
        counter.end()

        Log.d("Test_tag", "${name.methodName} time - " + counter.calcTimeMillis())

        assert(true)
    }

}