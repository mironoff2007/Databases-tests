package ru.mironov.sqlite

import android.annotation.SuppressLint
import android.database.Cursor
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull

@SuppressLint("Range")
fun Cursor.getLong(name: String): Long? {
    return this.checkRangeAndGet()?.getLongOrNull(this.getColumnIndex(name))
}

@SuppressLint("Range")
fun Cursor.getInt(name: String): Int? {
    return this.checkRangeAndGet()?.getIntOrNull(this.getColumnIndex(name))
}

@SuppressLint("Range")
fun Cursor.getString(name: String): String? {
    return this.checkRangeAndGet()?.getStringOrNull(this.getColumnIndex(name))
}

fun Cursor.checkRangeAndGet(): Cursor?{
    return if(this.count > 0) this else null
}