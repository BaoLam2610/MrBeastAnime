package com.lambao.base.presentation.ui.view.view_pager

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private var fragments: MutableList<Fragment> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: List<Fragment>) {
        fragments.clear()
        fragments.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}