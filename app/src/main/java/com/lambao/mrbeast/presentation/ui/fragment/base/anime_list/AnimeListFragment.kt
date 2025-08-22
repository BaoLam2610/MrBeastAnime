package com.lambao.mrbeast.presentation.ui.fragment.base.anime_list

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lambao.presentation.extension.getParcelableCompat
import com.lambao.presentation.extension.popBackStack
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimeListBinding

abstract class AnimeListFragment<T : Any, B : ViewDataBinding, VM : AnimeListViewModel<T>> :
    BaseVMFragment<FragmentAnimeListBinding, VM>() {

    private val _argData by lazy {
        arguments?.getParcelableCompat<AnimeListArgument>(Constants.Bundle.ARG)
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

    final override fun getLayoutResId() = R.layout.fragment_anime_list

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
        binding.btnBack.setOnClickListener {
            popBackStack()
        }
        binding.tvTitle.text = getTitleScreen()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _childBinding = null
    }
}