package com.woojugoing.junit

import org.junit.Test
import kotlin.test.assertEquals


class ServiceTest {

    private val service: Service = Service()

    @Test
    fun notYet() {
        // When
        val message = service.getMessage()

        // Then
        assertEquals("아직 시간이 좀 남았을 지도?", message)

    }
}