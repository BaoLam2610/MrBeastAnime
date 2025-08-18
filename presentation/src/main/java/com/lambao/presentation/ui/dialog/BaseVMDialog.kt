package com.lambao.presentation.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.lambao.data.network.NetworkException
import com.lambao.presentation.ui.state.ScreenState
import com.lambao.presentation.ui.viewmodel.BaseViewModel

abstract class BaseVMDialog<B : ViewDataBinding, VM : BaseViewModel> : BaseDialog<B>() {

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
        // initScreenState() // Pending implementation to match your preferred pattern
    }

    // protected open fun initScreenState() { /* Implement in your concrete dialog if needed */ }

    protected open fun getViewModelFactory(): ViewModelProvider.Factory =
        defaultViewModelProviderFactory
}


