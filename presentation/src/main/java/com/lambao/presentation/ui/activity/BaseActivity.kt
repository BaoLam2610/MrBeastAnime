package com.lambao.presentation.ui.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lambao.core.error.network.NetworkException
import com.lambao.presentation.handler.dialog.DialogHandler
import com.lambao.presentation.handler.dialog.DialogHandlerImpl
import com.lambao.presentation.handler.loading.LoadingDialogHandler
import com.lambao.presentation.handler.loading.LoadingHandler
import com.lambao.presentation.handler.network_error.NetworkErrorHandler
import com.lambao.presentation.handler.network_error.NetworkErrorHandlerImpl

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    private var _binding: B? = null
    protected val binding: B
        get() = _binding
            ?: throw IllegalStateException("Binding in ${this::class.java.simpleName} is null")

    protected open val dialogHandler: DialogHandler by lazy {
        DialogHandlerImpl(this)
    }

    protected open val loadingHandler: LoadingHandler by lazy {
        LoadingDialogHandler(this)
    }

    protected open val networkErrorHandler: NetworkErrorHandler by lazy {
        NetworkErrorHandlerImpl(
            this,
            dialogHandler
        )
    }

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    protected abstract fun onViewReady(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, getLayoutResId())
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        onViewReady(savedInstanceState)
    }

    open fun showLoading() {
        loadingHandler.showLoading()
    }

    open fun hideLoading() {
        loadingHandler.hideLoading()
    }

    open fun handleNetworkError(networkException: NetworkException) {
        networkErrorHandler.handleError(networkException)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) hideLoading()
        _binding = null
    }
}
