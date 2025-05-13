package com.lambao.mrbeast.domain.usecase.genres

import android.content.Context
import com.lambao.base.domain.FlowUseCase
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast_anime.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRandomBackgroundGenreUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatcherProvider: DispatcherProvider
) : FlowUseCase<Unit, List<Int>>(dispatcherProvider) {

    override val coroutineDispatcher get() = dispatcherProvider.mainDispatcher

    override fun execute(params: Unit?) = flow {
        val colors = context.resources.getIntArray(R.array.background_genres)
        emit(colors.toList())
    }
}