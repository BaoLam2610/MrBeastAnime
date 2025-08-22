package com.lambao.presentation.extension

import android.content.Context
import android.content.res.Resources.getSystem
import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.children
import androidx.core.widget.NestedScrollView
import com.lambao.presentation.ui.view.OnSingleClickListener

private const val SUPER_STATE = "SUPER_STATE"
private const val SPARSE_STATE_KEY = "SPARSE_STATE_KEY"

val Int.toPx: Int get() = (this / getSystem().displayMetrics.density).toInt()

val Int.toDp: Int get() = (this * getSystem().displayMetrics.density).toInt()

fun TextView.setHtml(html: String?) {
    if (html.isNullOrEmpty()) {
        text = ""
        return
    }
    this.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
}

fun View.click(func: (v: View) -> Unit) {
    setOnClickListener(
        object : OnSingleClickListener() {
            override fun onSingleClick(v: View?) {
                v?.let { func(it) }
            }
        }
    )
}

fun ViewGroup.saveChildViewStates(): SparseArray<Parcelable> {
    val childViewStates = SparseArray<Parcelable>()
    children.forEach { child -> child.saveHierarchyState(childViewStates) }
    return childViewStates
}

fun ViewGroup.restoreChildViewStates(childViewStates: SparseArray<Parcelable>) {
    children.forEach { child -> child.restoreHierarchyState(childViewStates) }
}

fun ViewGroup.saveInstanceState(state: Parcelable?): Parcelable? {
    return Bundle().apply {
        putParcelable(SUPER_STATE, state)
        putSparseParcelableArray(SPARSE_STATE_KEY, saveChildViewStates())
    }
}

fun ViewGroup.restoreInstanceState(state: Parcelable?): Parcelable? {
    var newState = state
    if (newState is Bundle) {
        val childrenState =
            newState.getSparseParcelableArrayCompat<Parcelable>(SPARSE_STATE_KEY)
        childrenState?.let { restoreChildViewStates(it) }
        newState = newState.getParcelableCompat(SUPER_STATE)
    }
    return newState
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun NestedScrollView.setOnLoadMoreListener(onLoadMore: () -> Unit) {
    setOnScrollChangeListener { v: NestedScrollView, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
        if (scrollY > oldScrollY) { // Check if scrolling down
            val lastChild = v.getChildAt(v.childCount - 1)
            if (lastChild != null) {
                if (scrollY >= (lastChild.measuredHeight - v.measuredHeight)) {
                    // Scrolled to the bottom (or very close)
                    onLoadMore()
                }
            }
        }
    }
}

/**
 * Extension function to hide the soft keyboard from a View.
 *
 * This function should typically be called on the View that currently has focus
 * or a parent View.
 */
fun View.hideKeyboard() {
    val imm = ContextCompat.getSystemService(context, InputMethodManager::class.java)
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Extension function to hide the soft keyboard using a Context.
 *
 * This is useful when you don't have a direct reference to the focused View,
 * but you have the current Activity or Fragment context.
 * It attempts to find the currently focused view in the window.
 *
 * @param view The current view in the window, typically `activity.currentFocus` or `fragment.view`.
 *             If null, it might not always work reliably.
 */
fun Context.hideKeyboard(view: View?) {
    val imm = ContextCompat.getSystemService(this, InputMethodManager::class.java)
    imm?.hideSoftInputFromWindow(view?.windowToken, 0)
}

/**
 * Extension function to show the soft keyboard and request focus for a View.
 *
 * This is typically called on an EditText or other focusable input View.
 */
fun View.showKeyboard() {
    if (requestFocus()) {
        val imm = ContextCompat.getSystemService(context, InputMethodManager::class.java)
        // Using SHOW_IMPLICIT can be more reliable in some cases
        // than SHOW_FORCED which is generally discouraged.
        imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}