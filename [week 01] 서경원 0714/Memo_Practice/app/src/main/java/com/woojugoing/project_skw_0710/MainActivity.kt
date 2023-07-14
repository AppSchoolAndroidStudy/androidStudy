package com.woojugoing.project_skw_0710

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.woojugoing.project_skw_0710.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    var rowPosition = 0

    companion object {
        const val MAIN_FRAGMENT = "Main Fragment"
        const val INPUT_FRAGMENT = "Input Fragment"
        const val RESULT_FRAGMENT = "Result Fragment"
        const val EDIT_FRAGMENT = "Edit Fragment"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        replaceFragment(MAIN_FRAGMENT, addToBackStack = false, animate = false)
    }

    fun replaceFragment(name: String, addToBackStack: Boolean, animate: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val newFragment = when (name) {
            MAIN_FRAGMENT -> MainFragment()
            INPUT_FRAGMENT -> InputFragment()
            RESULT_FRAGMENT -> ResultFragment()
            EDIT_FRAGMENT -> EditFragment()
            else -> Fragment()
        }

        fragmentTransaction.replace(R.id.container_main, newFragment)
        if (animate) {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(name)
        }
        fragmentTransaction.commit()
    }

    fun removeFragment(name: String) {
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

// 정보를 담을 객체
data class MemoClass(
    var idx: Int,
    var title: String,
    var content: String,
    var date: String,
)

