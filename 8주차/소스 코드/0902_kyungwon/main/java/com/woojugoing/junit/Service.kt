package com.woojugoing.junit

import java.time.Clock
import java.time.LocalDateTime

class Service {

    private val clock = Clock.systemDefaultZone()

    fun getMessage(): String {
        val hour = LocalDateTime.now(clock).hour
        val minute = LocalDateTime.now(clock).minute

        if(hour ==10 && minute >= 30) {
            return "스터디는 이미 시작했다고!"
        } else if(hour == 10 && minute < 30) {
            return "아직 시간이 좀 남았을 지도?"
        } else if(hour > 10) {
            return "넌 틀렸다..."
        } else {
            return "아직 시작도 안했는데요?"
        }
    }
}