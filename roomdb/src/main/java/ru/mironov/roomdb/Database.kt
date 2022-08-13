package ru.mironov.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TestObject::class], version = 1, exportSchema = false)
abstract class TestDatabase : RoomDatabase() {

    abstract fun testDao(): TestDao

    companion object {

        private const val DB_NAME = "test_db"

        fun getDatabase(context: Context): TestDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                TestDatabase::class.java,
                DB_NAME
            ).setJournalMode(JournalMode.TRUNCATE).build()
        }
    }
}
