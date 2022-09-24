package ru.mironov.domain

interface BaseDao {

    fun insert(obj: BaseTestDTO)

    fun insertLoop(list: List<BaseTestDTO>)

    fun insertAll(list: List<BaseTestDTO>)

    fun insertAllRawQuery(list: List<BaseTestDTO>)

    fun insertAllTransaction(list: List<BaseTestDTO>)

    fun getAll(): List<BaseTestDTO>

    fun resetTable()

    fun getRowsCount(): Int

    fun selectBetween(idStart: Int, idEnd:Int): List<BaseTestDTO>
}