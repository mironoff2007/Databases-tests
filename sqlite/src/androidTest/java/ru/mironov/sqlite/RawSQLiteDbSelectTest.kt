package ru.mironov.sqlite

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import ru.mironov.domain.Constants
import ru.mironov.domain.Constants.ADD_COUNT
import ru.mironov.domain.DbTest

@RunWith(AndroidJUnit4::class)
class RawSQLiteDbSelectTest {

    @get:Rule
    var name: TestName = TestName()

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private val dbTest = DbTest(DaoSQLite(appContext), this.javaClass.name)

    @Test
    fun selectBetweenTest() {
        val list = TestObject.createMockList(Constants.ADD_MILLION)

        val assertClear = fun(count: Int) {
            assertEquals(Constants.ADD_MILLION, count)
        }

        val idStart = 200000
        val idEnd = 210000

        val assertAddedCount = fun(count: Int) {
            assertEquals(idEnd - idStart, count)
        }

        dbTest.selectBetweenTest(list, assertClear, assertAddedCount, idStart, idEnd)
    }

}