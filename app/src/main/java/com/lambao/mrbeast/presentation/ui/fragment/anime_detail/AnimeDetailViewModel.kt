package com.lambao.mrbeast.presentation.ui.fragment.anime_detail

import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.model.anime.AnimePicture
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.domain.model.display.DisplayAnimeFullInfo
import com.lambao.mrbeast.domain.model.display.DisplayAnimePictureInfo
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeFullByIdUseCase
import com.lambao.mrbeast.domain.usecase.anime.GetAnimePicturesUseCase
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.episodes.AnimeEpisodesArgument
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.episodes.AnimeEpisodesFragment
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.videos_episodes.AnimeVideosEpisodesArgument
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.videos_episodes.AnimeVideosEpisodesFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val getAnimeFullByIdUseCase: GetAnimeFullByIdUseCase,
    private val getAnimePicturesUseCase: GetAnimePicturesUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _anime = MutableStateFlow<DisplayAnimeFullInfo?>(null)
    val anime = _anime.asStateFlow()

    private val _animePictures = MutableStateFlow<List<DisplayAnimePictureInfo>>(emptyList())
    val animePictures = _animePictures.asStateFlow()

    private val _fragments = _anime.map {
        if (it == null) return@map emptyList()
        if (it.getId().isNullOrEmpty()) return@map emptyList()
        buildList {
            if (it.isTvType() && it.hasBroadcast()) {
                add(
                    AnimeVideosEpisodesFragment.newInstance(
                        AnimeVideosEpisodesArgument(id = it.getId())
                    )
                )

                add(
                    AnimeEpisodesFragment.newInstance(
                        AnimeEpisodesArgument(
                            id = it.getId(),
                            thumbnail = it.displayThumbnail()
                        )
                    )
                )
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList<Fragment>())
    val fragments get() = _fragments

    private val _shouldShowFullInfo = _fragments.map {
        it.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)
    val shouldShowFullInfo get() = _shouldShowFullInfo

    fun fetchAnimeInfo(id: String) {
        handleData(getAnimeFullByIdUseCase.invoke(AnimeParams(id = id))) {
            _anime.emit(it)
            _animePictures.emit(
                listOf(
                    AnimePicture(
                        it.images?.jpg,
                        it.images?.webp
                    )
                )
            )
        }
    }

    fun fetchAnimePictures(id: String) {
        handleDataNoLoading(getAnimePicturesUseCase.invoke(AnimeParams(id = id))) {
            _animePictures.emit(it)
        }
    }
}