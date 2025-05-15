package com.lambao.mrbeast.presentation.ui.common.view_model.anime

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.model.anime.Anime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider), AnimeDelegate {

    private val _anime = MutableStateFlow<Anime?>(null)

    private val _animeId = MutableStateFlow("")

    override fun setAnime(anime: Anime?) {
        _anime.value = anime
    }

    override fun setAnimeId(id: String) {
        _animeId.value = id
    }

    override fun getAnime(): StateFlow<Anime?> = _anime

    override fun getAnimeId(): StateFlow<String> = _animeId
}