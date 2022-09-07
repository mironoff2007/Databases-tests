package ru.mironov.roomdb

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import ru.mironov.domain.Constants.ADD_COUNT
import ru.mironov.domain.DbTest


@RunWith(AndroidJUnit4::class)
class RoomDbTest {

    @get:Rule
    var name: TestName = TestName()

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private val dbTest = DbTest(DaoRoom(appContext),  this.javaClass.name)

    @Test
    fun insertTest() {
        val list = TestObject.createMockList(ADD_COUNT)

        val assertClear = fun(count: Int) {
            Assert.assertEquals(count, 0)
        }

        val assertAddedCount = fun(count: Int) {
            Assert.assertEquals(count, ADD_COUNT)
        }

        dbTest.insertTest(list, assertClear, assertAddedCount)

    }

    @Test
    fun insertAllTest() {
        val list = TestObject.createMockList(ADD_COUNT)

        val assertClear = fun(count: Int) {
            Assert.assertEquals(count, 0)
        }

        val assertAddedCount = fun(count: Int) {
            Assert.assertEquals(count, ADD_COUNT)
        }

        dbTest.insertAllTest(list, assertClear, assertAddedCount)
    }

}