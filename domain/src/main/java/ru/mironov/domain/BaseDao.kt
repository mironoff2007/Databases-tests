package ru.mironov.domain

interface BaseDao {

    fun insert(obj: BaseTestDTO)

    fun insertAll(list: List<BaseTestDTO>)

    fun getAll(): List<BaseTestDTO>

    fun resetTable()

    fun getRowsCount(): Int
}