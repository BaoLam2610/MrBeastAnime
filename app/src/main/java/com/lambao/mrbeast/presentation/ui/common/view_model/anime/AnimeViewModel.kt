package com.lambao.mrbeast.presentation.ui.common.view_model.anime

import androidx.lifecycle.viewModelScope
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.model.Trailer
import com.lambao.mrbeast.data.model.anime.Anime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider), AnimeDelegate {

    private val _anime = MutableStateFlow<Anime?>(null)

    private val _animeId = MutableStateFlow("")

    private val _trailer = MutableStateFlow<Trailer?>(null)

    private val _shouldShowTrailer = _trailer.map {
        !(it?.embedUrl.isNullOrEmpty() &&
                it?.youtubeId.isNullOrEmpty() &&
                it?.url.isNullOrEmpty())
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    override fun setAnime(anime: Anime?) {
        _anime.value = anime
    }

    override fun setAnimeId(id: String) {
        _animeId.value = id
    }

    override fun setAnimeTrailer(trailer: Trailer?) {
        _trailer.value = trailer
    }

    override fun getAnime(): StateFlow<Anime?> = _anime

    override fun getAnimeId(): StateFlow<String> = _animeId

    override fun getAnimeTrailer(): StateFlow<Trailer?> = _trailer

    override fun setShowTrailer(isShow: Boolean) = Unit

    override fun shouldShowTrailer(): StateFlow<Boolean> = _shouldShowTrailer
}