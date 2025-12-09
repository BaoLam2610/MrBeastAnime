package com.lambao.base.domain

import androidx.paging.PagingData
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider

abstract class PagingUseCase<Params, Result : Any>(
    dispatchProvider: DispatcherProvider
) : FlowUseCase<Params, PagingData<Result>>(dispatchProvider)