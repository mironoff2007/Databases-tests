package ru.mironov.sqldelight

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import ru.mironov.domain.Constants.ADD_COUNT
import ru.mironov.domain.Constants.ADD_MILLION
import ru.mironov.domain.DbTest

@RunWith(AndroidJUnit4::class)
class SqlDelightInsertTest {

    @get:Rule
    var name: TestName = TestName()

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private val dbTest = DatabaseDriverFactory(appContext).createDriver()

    @Test
    fun insertBySingleEmptyDBnoConfTest() {
        val list = TestObject.createMockList(ADD_COUNT)

        val assertClear = fun(count: Int) {
            assertEquals(count, 0)
        }

        val assertAddedCount = fun(count: Int) {
            assertEquals(count, ADD_COUNT)
        }

        dbTest.insertBySingleEmptyDBnoConfTest(list, assertClear, assertAddedCount)

    }

    @Test
    fun insertAllEmptyDBnoConfTest() {
        val list = TestObject.createMockList(ADD_COUNT)

        val assertClear = fun(count: Int) {
            assertEquals(count, 0)
        }

        val assertAddedCount = fun(count: Int) {
            assertEquals(count, ADD_COUNT)
        }

        dbTest.insertAllEmptyDBnoConfTest(list, assertClear, assertAddedCount)
    }

    @Test
    fun insertAllRawQueryEmptyDBnoConfTest() {
        val list = TestObject.createMockList(ADD_COUNT)

        val assertClear = fun(count: Int) {
            assertEquals(count, 0)
        }

        val assertAddedCount = fun(count: Int) {
            assertEquals(count, ADD_COUNT)
        }

        dbTest.insertAllRawEmptyDBnoConfTest(list, assertClear, assertAddedCount)
    }

    @Test
    fun insertAllTransactionDBnoConfTest() {
        val list = TestObject.createMockList(ADD_COUNT)

        val assertClear = fun(count: Int) {
            assertEquals(count, 0)
        }

        val assertAddedCount = fun(count: Int) {
            assertEquals(count, ADD_COUNT)
        }

        dbTest.insertAllTransactionTest(list, assertClear, assertAddedCount)
    }

    @Test
    fun insertMillionTest() {
        val list = TestObject.createMockList(ADD_MILLION)

        val assertClear = fun(count: Int) {
            assertEquals(count, 0)
        }

        val assertAddedCount = fun(count: Int) {
            assertEquals(count, ADD_MILLION)
        }

        dbTest.insertMillionTest(list, assertClear, assertAddedCount)
    }

}