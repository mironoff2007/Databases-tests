package ru.mironov.sqldelight_db

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import ru.mironov.domain.Constants.ADD_MILLION
import ru.mironov.domain.Constants.EXPECT_BETWEEN_STRING
import ru.mironov.domain.Constants.SELECT_BETWEEN_END
import ru.mironov.domain.Constants.SELECT_BETWEEN_START
import ru.mironov.domain.DbTest

@RunWith(AndroidJUnit4::class)
class SqlDelightSelectTest {

    @get:Rule
    var name: TestName = TestName()

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private val factory = DatabaseDriverFactory(appContext)
    private val dbSqlite = DaoSqlDelight(factory.getDataSource(), factory.createDriver())
    private val dbTest = DbTest(dao = dbSqlite, testName = this.javaClass.name)

    @Test
    fun selectBetweenTest() {
        val list = TestObject.createMockList(ADD_MILLION)

        val assertClear = fun(count: Int) {
            assertEquals(ADD_MILLION, count)
        }

        val idStart = SELECT_BETWEEN_START
        val idEnd =  SELECT_BETWEEN_END

        val assertAddedCount = fun(count: Int) {
            assertEquals(EXPECT_BETWEEN_STRING, count)
        }

        dbTest.selectBetweenTest(list, assertClear, assertAddedCount, idStart, idEnd)
    }

}