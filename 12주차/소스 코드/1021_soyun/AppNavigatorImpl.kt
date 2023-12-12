package com.test.study14_hilt

import androidx.fragment.app.FragmentActivity
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(private val activity: FragmentActivity) : AppNavigator {

    override fun navigateTo(screen: Screens) {
        val fragment = when (screen) {
            Screens.FRAGMENT1 -> Fragment1()
            Screens.FRAGMENT2 -> Fragment2()
        }

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }
}