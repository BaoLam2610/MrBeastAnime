package com.lambao.presentation.ui.bottom_sheet

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.lambao.presentation.extension.observeLatest
import com.lambao.presentation.ui.event.UiEvent
import com.lambao.presentation.ui.state.UiState
import com.lambao.presentation.ui.viewmodel.BaseViewModel

abstract class BaseVMBottomSheet<B : ViewDataBinding, VM : BaseViewModel<UiState, UiEvent>> :
    BaseBottomSheet<B>() {

    protected abstract val viewModel: VM

    protected abstract fun getViewModelClass(): Class<VM>

    protected abstract fun initObserve()

    protected abstract fun handleUiState(state: UiState)

    protected abstract fun handleUiEvent(event: UiEvent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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


