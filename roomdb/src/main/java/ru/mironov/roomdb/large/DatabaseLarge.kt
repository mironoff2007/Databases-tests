package ru.mironov.roomdb.large

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.mironov.domain.LargeObject.DB_LARGE_NAME

@Database(entities = [TestLargeObject::class], version = 1, exportSchema = false)
abstract class TestLargeDatabase : RoomDatabase() {

    abstract fun testLargeDao(): TestLargeDao

    companion object {

        fun getDatabase(context: Context): TestLargeDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                TestLargeDatabase::class.java,
                DB_LARGE_NAME
            ).setJournalMode(JournalMode.TRUNCATE).build()
        }
    }
}
