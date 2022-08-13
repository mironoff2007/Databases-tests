package ru.mironov.roomdb

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DbTest {

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun addAndGetTest() {

        val dao = TestDatabase.getDatabase(appContext).testDao()

        dao.resetTable()
        dao.resetCounter()

        dao.addObject(TestObject(name = "name 1"))
        dao.addObject(TestObject(name = "name 2"))

        val list = dao.getObjects()
        assertEquals(list.size, 2)
    }

}