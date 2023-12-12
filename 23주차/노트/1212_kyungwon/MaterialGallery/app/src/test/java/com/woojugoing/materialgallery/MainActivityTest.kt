package com.woojugoing.materialgallery

import junit.framework.TestCase.assertEquals
import org.junit.Test


internal class MainActivityTest {

    private val mainActivity: _MainActivity = _MainActivity()

    @Test
    fun testImg() {
        val a1 = ""
        assertEquals(null, mainActivity.activityMainBinding.toolbarMain.title)
    }
}