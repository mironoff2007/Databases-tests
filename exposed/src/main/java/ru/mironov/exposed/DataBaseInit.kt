package ru.mironov.exposed


import android.content.Context
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.vendors.H2Dialect
import org.jetbrains.exposed.sql.vendors.SQLiteDialect

fun Context.initDatabase(): Database {
    return Database.connect(
        "jdbc:${SQLiteDialect.dialectName}:/data/data/${packageName}/expose_database.db"
        //"jdbc:${H2Dialect.dialectName}:/data/data/${packageName}/expose_database_h2.db"
    )
}