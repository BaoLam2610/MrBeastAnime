package com.lambao.presentation.ui.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lambao.core.error.network.NetworkException
import com.lambao.presentation.handler.dialog.DialogHandler
import com.lambao.presentation.handler.dialog.DialogHandlerImpl
import com.lambao.presentation.handler.loading.LoadingDialogHandler
import com.lambao.presentation.handler.loading.LoadingHandler
import com.lambao.presentation.handler.network_error.NetworkErrorHandler
import com.lambao.presentation.handler.network_error.NetworkErrorHandlerImpl

abstract class BaseBottomSheet<B : ViewDataBinding> : BottomSheetDialogFragment() {

    private var _binding: B? = null
    protected val binding: B
        get() = _binding
            ?: throw IllegalStateException("Binding in ${this::class.java.simpleName} is null")

    protected open val dialogHandler: DialogHandler by lazy {
        DialogHandlerImpl(requireActivity())
    }

    protected open val loadingHandler: LoadingHandler by lazy {
        LoadingDialogHandler(requireActivity())
    }

    protected open val networkErrorHandler: NetworkErrorHandler by lazy {
        NetworkErrorHandlerImpl(
            requireContext(),
            dialogHandler
        )
    }

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    protected abstract fun onViewReady(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady(savedInstanceState)
    }

    fun showLoading() {
        loadingHandler.showLoading()
    }

    fun hideLoading() {
        loadingHandler.hideLoading()
    }

    fun handleNetworkError(networkException: NetworkException) {
        networkErrorHandler.handleError(networkException)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideLoading()
        _binding = null
    }
}


