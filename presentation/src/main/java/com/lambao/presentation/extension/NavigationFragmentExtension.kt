package com.lambao.presentation.extension

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.lambao.presentation.R
import kotlinx.coroutines.launch

fun Fragment.navigate(
    @IdRes actionId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
) {
    try {
        val navController = findNavController()
        val currentDestination = navController.currentDestination
        if (currentDestination is FragmentNavigator.Destination &&
            currentDestination.className == this::class.java.name
        ) {
            navController.navigate(
                actionId,
                args,
                navOptions ?: navOptions {
                    anim {
                        enter = R.anim.fade_in
                        exit = R.anim.fade_out
                        popExit = R.anim.fade_out
                        popEnter = R.anim.fade_in
                    }
                }
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Fragment.tryNavigate(
    @IdRes actionId: Int,
    args: Bundle? = null,
    navOptions: NavOptions.Builder.() -> Unit = {},
) {
    try {
        findNavController().navigate(
            actionId,
            args,
            NavOptions.Builder()
                .setEnterAnim(R.anim.fade_in)
                .setExitAnim(R.anim.fade_out)
                .setPopEnterAnim(R.anim.fade_in)
                .setPopExitAnim(R.anim.fade_out)
                .apply(navOptions)
                .build()
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Fragment.popBackStack(): Boolean {
    return try {
        findNavController().popBackStack()
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun Fragment.canGoBack(): Boolean {
    return try {
        findNavController().previousBackStackEntry != null
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun Fragment.getCurrentFragment(): Fragment? {
    return try {
        val navController = findNavController()
        val currentBackStackEntry = navController.currentBackStackEntry
        currentBackStackEntry?.let { entry ->
            (parentFragmentManager.findFragmentByTag(entry.id) as? NavHostFragment)
                ?.childFragmentManager?.fragments?.firstOrNull()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun <T> Fragment.navigateForResult(
    @IdRes actionId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    resultKey: String,
    onResult: (T) -> Unit
) {
    try {
        val navController = findNavController()
        val currentDestination = navController.currentDestination
        if (currentDestination is FragmentNavigator.Destination &&
            currentDestination.className == this::class.java.name
        ) {
            val currentBackStackEntry = navController.currentBackStackEntry
            currentBackStackEntry?.let { entry ->
                val resultFlow = entry.savedStateHandle.getStateFlow<T?>(resultKey, null)
                viewLifecycleOwner.lifecycleScope.launch {
                    resultFlow.collect { result ->
                        result?.let {
                            onResult(it)
                            entry.savedStateHandle[resultKey] = null
                        }
                    }
                }
                navController.navigate(actionId, args, navOptions)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun <T> Fragment.setNavigationResult(resultKey: String, result: T) {
    try {
        val navController = findNavController()
        navController.previousBackStackEntry?.savedStateHandle?.set(resultKey, result)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


