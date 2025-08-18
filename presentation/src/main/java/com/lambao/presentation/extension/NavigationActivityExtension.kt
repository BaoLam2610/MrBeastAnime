package com.lambao.presentation.extension

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.launch

fun AppCompatActivity.findNavController(@IdRes navHostFragmentId: Int): NavController {
    val navHostFragment =
        supportFragmentManager.findFragmentById(navHostFragmentId) as NavHostFragment
    return navHostFragment.navController
}

fun AppCompatActivity.navigate(
    @IdRes navHostFragmentId: Int,
    @IdRes actionId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
) {
    try {
        val navController = findNavController(navHostFragmentId)
        navController.navigate(actionId, args, navOptions)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun AppCompatActivity.popBackStack(@IdRes navHostFragmentId: Int): Boolean {
    return try {
        findNavController(navHostFragmentId).popBackStack()
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun AppCompatActivity.canGoBack(@IdRes navHostFragmentId: Int): Boolean {
    return try {
        findNavController(navHostFragmentId).previousBackStackEntry != null
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun AppCompatActivity.getCurrentFragment(@IdRes navHostFragmentId: Int): Fragment? {
    return try {
        val navController = findNavController(navHostFragmentId)
        val currentBackStackEntry = navController.currentBackStackEntry
        currentBackStackEntry?.let {
            (supportFragmentManager.findFragmentById(navHostFragmentId) as? NavHostFragment)
                ?.childFragmentManager?.fragments?.firstOrNull()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun <T> AppCompatActivity.navigateForResult(
    @IdRes navHostFragmentId: Int,
    @IdRes actionId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    resultKey: String,
    onResult: (T) -> Unit
) {
    try {
        val navController = findNavController(navHostFragmentId)
        val currentBackStackEntry = navController.currentBackStackEntry
        currentBackStackEntry?.let { entry ->
            val resultFlow = entry.savedStateHandle.getStateFlow<T?>(resultKey, null)
            lifecycleScope.launch {
                resultFlow.collect { result ->
                    result?.let {
                        onResult(it)
                        entry.savedStateHandle[resultKey] = null
                    }
                }
            }
            navController.navigate(actionId, args, navOptions)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun <T> AppCompatActivity.setNavigationResult(
    @IdRes navHostFragmentId: Int,
    resultKey: String,
    result: T
) {
    try {
        val navController = findNavController(navHostFragmentId)
        navController.previousBackStackEntry?.savedStateHandle?.set(resultKey, result)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


