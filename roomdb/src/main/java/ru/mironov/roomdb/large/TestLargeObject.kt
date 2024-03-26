package ru.mironov.roomdb.large

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.mironov.domain.LargeObject
import ru.mironov.domain.LargeObject.BLOB_3MB_SIZE

@Entity
class TestLargeObject(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var blob: ByteArray = LargeObject.getBlob(BLOB_3MB_SIZE)
) {


}