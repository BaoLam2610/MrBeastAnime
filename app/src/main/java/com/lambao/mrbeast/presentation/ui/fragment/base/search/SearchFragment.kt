package com.lambao.mrbeast.presentation.ui.fragment.base.search

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lambao.base.extension.getParcelableCompat
import com.lambao.base.extension.observeLatest
import com.lambao.base.extension.popBackStack
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentSearchBinding

abstract class SearchFragment<T : Any, B : ViewDataBinding, VM : SearchViewModel<T>> :
    BaseVMFragment<FragmentSearchBinding, VM>() {

    private val _argData by lazy {
        arguments?.getParcelableCompat<SearchArgument>(Constants.Bundle.ARG)
    }
    protected open val argData get() = _argData

    private var _childBinding: B? = null
    protected val childBinding: B
        get() = _childBinding
            ?: throw IllegalStateException("Binding in ${this::class.java.simpleName} is null")

    @LayoutRes
    abstract fun getChildLayoutResId(): Int

    abstract fun onChildViewReady(savedInstanceState: Bundle?)

    final override fun getLayoutResId() = R.layout.fragment_search

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

    override fun initObserve() {
        binding.viewModel = viewModel
        observeLatest(viewModel.shouldShowClearValue())
    }

    private fun setupCommonViews() {
        binding.btnBack.setOnClickListener {
            popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _childBinding = null
    }
}