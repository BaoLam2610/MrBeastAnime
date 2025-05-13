package com.lambao.mrbeast.domain.usecase.watch

import com.lambao.base.data.Resource
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.watch.WatchEpisode
import com.lambao.mrbeast.data.remote.params.watch.WatchParams
import com.lambao.mrbeast.data.repository.watch.WatchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWatchPopularEpisodesUseCase @Inject constructor(
    private val watchRepository: WatchRepository,
    dispatcherProvider: DispatcherProvider
) : WatchAnimeUseCase<WatchEpisode>(dispatcherProvider) {
    override fun execute(params: WatchParams?): Flow<Resource<List<WatchEpisode>>> {
        return watchRepository.getWatchPopularEpisodes()
    }
}