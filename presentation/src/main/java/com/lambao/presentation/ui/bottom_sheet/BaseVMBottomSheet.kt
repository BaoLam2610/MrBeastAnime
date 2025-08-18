package com.lambao.presentation.ui.bottom_sheet

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.lambao.data.network.NetworkException
import com.lambao.presentation.extension.observeLatest
import com.lambao.presentation.ui.state.ScreenState
import com.lambao.presentation.ui.viewmodel.BaseViewModel

abstract class BaseVMBottomSheet<B : ViewDataBinding, VM : BaseViewModel> : BaseBottomSheet<B>() {

    protected lateinit var viewModel: VM

    protected abstract fun getViewModelClass(): Class<VM>

    protected abstract fun initObserve()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, getViewModelFactory())[getViewModelClass()]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initScreenState()
    }

    protected open fun initScreenState() {
        observeLatest(viewModel.screenState) { state ->
            when (state) {
                is ScreenState.Loading -> showLoading()
                is ScreenState.Error -> {
                    hideLoading()
                    if (state.throwable is NetworkException) {
                        handleNetworkError(state.throwable)
                        return@observeLatest
                    }
                    state.throwable.message?.let { dialogHandler.showAlertDialog(it) }
                }
                else -> hideLoading()
            }
        }
    }

    protected open fun getViewModelFactory(): ViewModelProvider.Factory =
        defaultViewModelProviderFactory
}


