package ru.mironov.domain

interface BaseDao {

    fun insert(obj: BaseTestDto)

    fun getAll(): List<BaseTestDto>

    fun resetTable()
}