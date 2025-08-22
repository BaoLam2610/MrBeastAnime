package com.lambao.presentation.extension

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun Context.getAppName(): String {
    return try {
        val applicationInfo = applicationInfo
        val stringId = applicationInfo.labelRes
        if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else getString(stringId)
    } catch (e: Exception) {
        ""
    }
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun FragmentActivity.showToast(message: String) {
    this.showToast(message = message, duration = Toast.LENGTH_SHORT)
}

fun Fragment.showToast(message: String) {
    requireContext().showToast(message)
}


