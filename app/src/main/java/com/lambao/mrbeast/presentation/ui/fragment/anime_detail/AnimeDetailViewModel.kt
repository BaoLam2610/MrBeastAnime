package com.lambao.mrbeast.presentation.ui.fragment.anime_detail

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.model.anime.Anime
import com.lambao.mrbeast.data.model.anime.AnimePicture
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.domain.model.display.DisplayAnimeFullInfo
import com.lambao.mrbeast.domain.model.display.DisplayAnimePictureInfo
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeFullByIdUseCase
import com.lambao.mrbeast.domain.usecase.anime.GetAnimePicturesUseCase
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.characters.AnimeCharactersArgument
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.characters.AnimeCharactersFragment
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.episodes.AnimeEpisodesArgument
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.episodes.AnimeEpisodesFragment
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.more_info.AnimeMoreInfoArgument
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.more_info.AnimeMoreInfoFragment
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.videos_episodes.AnimeVideosEpisodesArgument
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.videos_episodes.AnimeVideosEpisodesFragment
import com.lambao.mrbeast_anime.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext private val context: Context,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _anime = MutableStateFlow<DisplayAnimeFullInfo?>(null)
    val anime = _anime.asStateFlow()

    private val _animePictures = MutableStateFlow<List<DisplayAnimePictureInfo>>(emptyList())
    val animePictures = _animePictures.asStateFlow()

    private val _screenTypes = _anime.map {
        if (it == null) return@map emptyList()
        if (it.getId().isNullOrEmpty()) return@map emptyList()
        buildList {
            if (it.isTvType() && it.hasBroadcast()) {
                add(
                    AnimeDetailScreenType(
                        title = context.getString(R.string.episode),
                        fragment = AnimeVideosEpisodesFragment.newInstance(
                            AnimeVideosEpisodesArgument(id = it.getId())
                        )
                    )
                )

                add(
                    AnimeDetailScreenType(
                        title = context.getString(R.string.broadcast),
                        fragment = AnimeEpisodesFragment.newInstance(
                            AnimeEpisodesArgument(
                                id = it.getId(),
                                thumbnail = it.displayThumbnail()
                            )
                        )
                    )
                )
            }

            add(
                AnimeDetailScreenType(
                    title = context.getString(R.string.info),
                    fragment = AnimeMoreInfoFragment.newInstance(
                        AnimeMoreInfoArgument(it as Anime)
                    )
                )
            )

            add(
                AnimeDetailScreenType(
                    title = context.getString(R.string.character),
                    fragment = AnimeCharactersFragment.newInstance(
                        AnimeCharactersArgument(it.getId())
                    )
                )
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList<AnimeDetailScreenType>())
    val screenTypes get() = _screenTypes

    private val _fragments = _screenTypes.map {
        it.map { item -> item.fragment }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val fragments get() = _fragments

    private val _shouldShowFullInfo = _screenTypes.map {
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