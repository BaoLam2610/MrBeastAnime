package com.lambao.mrbeast.presentation.ui.fragment.base.filter

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lambao.presentation.extension.getParcelableCompat
import com.lambao.presentation.extension.popBackStack
import com.lambao.base.presentation.ui.bottom_sheet.BaseVMBottomSheet
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentFilterBinding

abstract class FilterBottomSheet<B : ViewDataBinding, V : FilterViewModel> :
    BaseVMBottomSheet<FragmentFilterBinding, V>() {
    private val _argData by lazy {
        arguments?.getParcelableCompat<FilterArgument>(Constants.Bundle.ARG)
    }
    protected open val argData get() = _argData

    private var _childBinding: B? = null
    protected val childBinding: B
        get() = _childBinding
            ?: throw IllegalStateException("Binding in ${this::class.java.simpleName} is null")

    @LayoutRes
    abstract fun getChildLayoutResId(): Int

    abstract fun getTitleScreen(): String

    abstract fun onChildViewReady(savedInstanceState: Bundle?)

    final override fun getLayoutResId() = R.layout.fragment_filter

    final override fun onViewReady(savedInstanceState: Bundle?) {
        if (!binding.layoutChild.isInflated) {
            binding.layoutChild.viewStub?.layoutResource = getChildLayoutResId()
            binding.layoutChild.viewStub?.inflate()
            binding.layoutChild.binding?.root?.let {
                _childBinding = DataBindingUtil.bind(it)
            }
            _childBinding?.apply {
                lifecycleOwner = viewLifecycleOwner
                executePendingBindings()
                onChildViewReady(savedInstanceState)
            }
        }
        setupCommonViews()
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupCommonViews() {
        binding.btnClose.setOnClickListener {
            popBackStack()
        }
        binding.tvTitle.text = getTitleScreen()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _childBinding = null
    }
}