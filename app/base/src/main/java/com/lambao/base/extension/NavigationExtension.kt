package com.lambao.base.extension

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Sets up BottomNavigationView with NavController, ensuring Fragments are reused
 * and avoiding unnecessary recreation.
 */
fun BottomNavigationView.setupWithNavController2(navController: NavController) {
    setOnItemSelectedListener { item ->
        val navOptions = NavOptions.Builder()
            .setPopUpTo(navController.graph.startDestinationId, false)
            .setLaunchSingleTop(true) // Reuse existing Fragment instance
            .build()

        try {
            navController.navigate(item.itemId, null, navOptions)
            true
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            false
        }
    }

    // Handle reselect to prevent re-emitting states
    setOnItemReselectedListener { item ->
        // Optionally, pop back stack to ensure fresh state
        navController.popBackStack(item.itemId, false)
    }
}