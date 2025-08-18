package com.lambao.presentation.extension

import android.content.Context
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

private const val SUPER_STATE = "SUPER_STATE"
private const val SPARSE_STATE_KEY = "SPARSE_STATE_KEY"

val Int.toPx: Int get() = (this / android.content.res.Resources.getSystem().displayMetrics.density).toInt()
val Int.toDp: Int get() = (this * android.content.res.Resources.getSystem().displayMetrics.density).toInt()

fun TextView.setHtml(html: String?) {
    if (html.isNullOrEmpty()) {
        text = ""
        return
    }
    this.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
}

fun View.click(func: (v: View) -> Unit) {
    setOnClickListener { v -> func(v) }
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
    return android.os.Bundle().apply {
        putParcelable(SUPER_STATE, state)
        putSparseParcelableArray(SPARSE_STATE_KEY, saveChildViewStates())
    }
}

fun ViewGroup.restoreInstanceState(state: Parcelable?): Parcelable? {
    var newState = state
    if (newState is android.os.Bundle) {
        val childrenState =
            newState.getSparseParcelableArray<Parcelable>(SPARSE_STATE_KEY)
        childrenState?.let { restoreChildViewStates(it) }
        newState = newState.getParcelable(SUPER_STATE)
    }
    return newState
}

fun View.visible() { visibility = View.VISIBLE }
fun View.invisible() { visibility = View.INVISIBLE }
fun View.gone() { visibility = View.GONE }

fun NestedScrollView.setOnLoadMoreListener(onLoadMore: () -> Unit) {
    setOnScrollChangeListener { v: NestedScrollView, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
        if (scrollY > oldScrollY) {
            val lastChild = v.getChildAt(v.childCount - 1)
            if (lastChild != null) {
                if (scrollY >= (lastChild.measuredHeight - v.measuredHeight)) {
                    onLoadMore()
                }
            }
        }
    }
}

fun View.hideKeyboard() {
    val imm = ContextCompat.getSystemService(context, InputMethodManager::class.java)
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

fun Context.hideKeyboard(view: View?) {
    val imm = ContextCompat.getSystemService(this, InputMethodManager::class.java)
    imm?.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun View.showKeyboard() {
    if (requestFocus()) {
        val imm = ContextCompat.getSystemService(context, InputMethodManager::class.java)
        imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}


