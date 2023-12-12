package com.test.study14_hilt

enum class Screens {
    FRAGMENT1,
    FRAGMENT2
}

interface AppNavigator {
    fun navigateTo(screen: Screens)
}