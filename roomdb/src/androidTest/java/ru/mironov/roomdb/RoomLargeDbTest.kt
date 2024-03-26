package ru.mironov.roomdb

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import ru.mironov.domain.Constants.ADD_COUNT
import ru.mironov.domain.Constants.ADD_MILLION
import ru.mironov.domain.DbTest
import ru.mironov.domain.LargeObject
import ru.mironov.roomdb.large.DaoLargeRoom
import ru.mironov.roomdb.large.TestLargeObject
import java.lang.Exception

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class RoomLargeDbTest {

    @get:Rule
    var name: TestName = TestName()

    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private val db = DaoLargeRoom(appContext)

    @After
    fun after() {
        db.resetTable()
    }

    @Test
    fun testExceptionTooLarge() {
        val obj = TestLargeObject()
        db.insert(obj)
        try {
            db.get()
            assert(false)
        } catch (e: android.database.sqlite.SQLiteBlobTooBigException) {
            assert(true)
        }
    }

    @Test
    fun testIsInSizeLimit() {
        val obj = TestLargeObject()
        obj.blob = LargeObject.getBlob(LargeObject.BLOB_1MB_SIZE)
        db.insert(obj)
        val result = db.get()
        assertEquals(1, result.size)
    }

    @Test
    fun testQueryList() {
        val repeat = 10
        val list = mutableListOf<TestLargeObject>()
        repeat(repeat) {
            list.add(
                TestLargeObject(
                    id = it + 1,
                    blob = LargeObject.getBlob(LargeObject.BLOB_1MB_SIZE)
                )
            )
        }
        db.insert(list)
        val result = db.get()
        assertEquals(repeat, result.size)
    }

}