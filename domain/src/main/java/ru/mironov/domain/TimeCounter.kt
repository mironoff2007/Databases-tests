package ru.mironov.domain

class TimeCounter() {

    private var startTime = 0L

    private var endTime = 0L

    fun end() {
        endTime = System.currentTimeMillis()
    }

    fun start() {
        startTime = System.currentTimeMillis()
    }

    fun calcTimeMillis(): Long {
        return endTime - startTime
    }

}