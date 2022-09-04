package ru.mironov.domain

class TimeCounter() {

    private val laps =  mutableListOf<Long>()

    private var startTime = 0L

    private var endTime = 0L

    private var pauseStart = 0L

    fun end() {
        endTime = System.currentTimeMillis()
    }

    fun start() {
        laps.clear()
        endTime = 0
        startTime = System.currentTimeMillis()
    }

    fun calcTimeMillis(): Long {
        if (endTime == 0L) return 0
        return endTime - startTime
    }

    fun next(){
        val currentTime = System.currentTimeMillis()
        val time = currentTime - startTime
        laps.add(time)
        startTime = currentTime
    }

    fun getAvg(): Long {
        var avg = 0L
        laps.forEach {
            avg += it/ laps.size
        }
        return avg
    }

    fun getWorst(): Long {
        return laps.maxOf { it }
    }

    fun pause(){
        pauseStart = System.currentTimeMillis()
    }

    fun resume() {
        startTime = startTime + System.currentTimeMillis() - pauseStart
        pauseStart = 0
    }

}