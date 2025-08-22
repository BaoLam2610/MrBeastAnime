package com.lambao.presentation.ui.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.lambao.presentation.extension.observeLatest
import com.lambao.presentation.ui.event.UiEvent
import com.lambao.presentation.ui.state.UiState
import com.lambao.presentation.ui.viewmodel.BaseViewModel

abstract class BaseVMActivity<B : ViewDataBinding, VM : BaseViewModel<UiState, UiEvent>> :
    BaseActivity<B>() {

    protected abstract val viewModel: VM

    protected abstract fun getViewModelClass(): Class<VM>

    protected abstract fun initObserve()

    protected abstract fun handleUiState(state: UiState)

    protected abstract fun handleUiEvent(event: UiEvent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initScreenState()
        initObserve()
    }

    protected open fun initScreenState() {
        observeLatest(viewModel.uiState) { state ->
            handleUiState(state)
        }

        observeLatest(viewModel.uiEvent) { event ->
            handleUiEvent(event)
        }
    }
}
