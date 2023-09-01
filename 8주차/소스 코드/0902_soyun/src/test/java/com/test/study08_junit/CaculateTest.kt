package com.test.study08_junit

import org.junit.Assert.assertEquals
import org.junit.Test

class CaculateTest {

    @Test
    fun testAdd(){
        val caculate = Caculate()
        val result = caculate.add(5,8)
        assertEquals(13,result)
    }

    @Test
    fun testSubstract(){
        val caculate = Caculate()
        val result = caculate.substract(9,8)
        assertEquals(1,result)
    }

    @Test
    fun testMutltiply(){
        val caculate = Caculate()
        val result = caculate.multiply(9,8)
        assertEquals(72,result)
    }
}