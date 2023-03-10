package ru.mironov.domain

abstract class BaseTestDTO(
    open val name: String,
    open val date: String,
    open val relationId: Int
) {

    companion object {

        const val ID_FIELD_NAME = "id"
        const val NAME_FIELD_NAME = "name"
        const val DATE_FIELD_NAME = "date"
        const val RELATION_ID_FIELD_NAME = "relationId"

    }
}