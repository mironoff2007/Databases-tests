package ru.mironov.sqlite

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class DbTest {

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

}