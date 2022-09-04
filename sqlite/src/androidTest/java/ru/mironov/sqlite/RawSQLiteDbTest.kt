package ru.mironov.sqlite

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import ru.mironov.domain.Constants.ADD_COUNT
import ru.mironov.domain.DbTest

@RunWith(AndroidJUnit4::class)
class RawSQLiteDbTest {

    @get:Rule
    var name: TestName = TestName()

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private val dbTest = DbTest(TestDaoSQLite(appContext), this.javaClass.name)


    @Test
    fun insertTest() {
        val list = TestObject.createMockList(ADD_COUNT)

        dbTest.insertTest(list)

        assert(true)
    }

}