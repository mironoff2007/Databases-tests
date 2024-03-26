package ru.mironov.domain

object LargeObject {

    fun getBlob(size: Int): ByteArray {
        val blob = ByteArray(size)
        repeat(size) {
            blob[it] = Byte.MAX_VALUE
        }
        return blob
    }

    const val BLOB_3MB_SIZE: Int = 3_000_000
    const val BLOB_1MB_SIZE: Int = 1_000_000
    const val DB_LARGE_NAME = "db_large"
    const val TABLE_LARGE_NAME = "TestLargeObject"
}