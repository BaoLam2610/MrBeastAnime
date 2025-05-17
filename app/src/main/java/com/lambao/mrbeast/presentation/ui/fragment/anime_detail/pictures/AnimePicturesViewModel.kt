package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.pictures

import androidx.lifecycle.viewModelScope
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.paging.ClientPagingViewModel
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.domain.model.display.DisplayAnimePictureInfo
import com.lambao.mrbeast.domain.usecase.anime.GetAnimePicturesUseCase
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.empty_data.EmptyDataDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.empty_data.EmptyDataViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnimePicturesViewModel @Inject constructor(
    private val getAnimePicturesUseCase: GetAnimePicturesUseCase,
    dispatcherProvider: DispatcherProvider
) : ClientPagingViewModel<DisplayAnimePictureInfo>(dispatcherProvider),
    AnimeDelegate by AnimeViewModel(dispatcherProvider),
    EmptyDataDelegate by EmptyDataViewModel(dispatcherProvider) {

    private val _youtubePlayer = MutableStateFlow<YouTubePlayer?>(null)
    val youTubePlayer = _youtubePlayer.asStateFlow()

    private val _shouldShowTrailer = getAnimeTrailer().map {
        !(it?.embedUrl.isNullOrEmpty() &&
                it?.youtubeId.isNullOrEmpty() &&
                it?.url.isNullOrEmpty())
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun setYoutubePlayer(player: YouTubePlayer) {
        _youtubePlayer.value = player
    }

    fun loadYoutubeVideo(youtubeId: String?) {
        if (youtubeId.isNullOrEmpty()) return
        _youtubePlayer.value?.cueVideo(youtubeId, 0f)
    }

    fun pauseYoutubeVideo() {
        _youtubePlayer.value?.pause()
    }

    override fun shouldShowTrailer() = _shouldShowTrailer

    fun fetchAnimePictures(id: String) {
        setAnimeId(id)
        fetchData()
    }

    override fun fetchData() {
        handleData(
            getAnimePicturesUseCase.invoke(AnimeParams(id = getAnimeId().value)),
            onError = {
                setShowEmptyData(true)
            }
        ) {
            setFullItemList(it)
            setShowEmptyData(it.isEmpty())
        }
    }
}