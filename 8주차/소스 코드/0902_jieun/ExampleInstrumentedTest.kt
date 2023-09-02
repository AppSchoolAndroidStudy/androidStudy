package com.test.retrofitex

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.test.retrofitex.server.RetrofitClient
import com.test.retrofitex.ui.ResultActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.text.SimpleDateFormat

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.test.retrofitex", appContext.packageName)
//    }

    @get:Rule
    val activityRule = ActivityTestRule(ResultActivity::class.java)

    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val date = sdf.format(System.currentTimeMillis())

//    @Before
//    fun setUp(){
//        resultActivity = ResultActivity()
//    }

    @Test
    fun explanation_isNotNull(){
        val activity = activityRule.activity
        val explanation = activity.getIntentString("explanation")
        assertNull(explanation)
    }

    @Test
    fun hdurl_isNotNull(){
        val activity = activityRule.activity
        val hdurl = activity.getIntentString("hdurl")
        assertNull(hdurl)
    }

    @Test
    fun title_isNotNull(){
        val activity = activityRule.activity
        val title = activity.getIntentString("title")
        assertNull(title)
    }

    @Test
    fun date_isToday(){
        val activity = activityRule.activity
        //현재 날짜와 인텐트로 받은 date 값이 같은지 검사
        assertEquals(date, activity.getIntentString("date"))
    }
}