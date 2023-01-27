package ru.mironov.sqldelight_db

import ru.mironov.sqldelight_db.TestObjectsDb
import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class DatabaseDriverFactory(private val context: Context) {

    fun createDriver(): SqlDriver {
        if(driver == null) {
            driver = AndroidSqliteDriver(
                schema = TestObjectsDb.Schema,
                context = context,
                name = "testObject.db"
            )
        }
        return driver!!
    }

    fun getDataSource(): TestObjectsDb {
        return TestObjectsDb(createDriver())
    }

    companion object {
        private var driver: SqlDriver? = null
    }
}