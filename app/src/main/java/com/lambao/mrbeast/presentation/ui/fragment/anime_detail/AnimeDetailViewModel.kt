package com.lambao.mrbeast.presentation.ui.fragment.anime_detail

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.model.anime.Anime
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.domain.model.display.DisplayAnimeFullInfo
import com.lambao.mrbeast.domain.model.screen.TabLayoutScreenType
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeFullByIdUseCase
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.characters.AnimeCharactersArgument
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.characters.AnimeCharactersFragment
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.episodes.AnimeEpisodesArgument
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.episodes.AnimeEpisodesFragment
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.more_info.AnimeMoreInfoArgument
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.more_info.AnimeMoreInfoFragment
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.pictures.AnimePicturesArgument
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.pictures.AnimePicturesFragment
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.recommendations.AnimeRecommendationsArgument
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.recommendations.AnimeRecommendationsFragment
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.videos_episodes.AnimeVideosEpisodesArgument
import com.lambao.mrbeast.presentation.ui.fragment.anime_detail.videos_episodes.AnimeVideosEpisodesFragment
import com.lambao.mrbeast_anime.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val getAnimeFullByIdUseCase: GetAnimeFullByIdUseCase,
    @ApplicationContext private val context: Context,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _sourceFragmentId = MutableStateFlow(R.id.homeFragment)

    private val _anime = MutableStateFlow<DisplayAnimeFullInfo?>(null)
    val anime = _anime.asStateFlow()

    private val _shouldShowButtonPlay = _anime.map {
        return@map it != null && !it.isUpcoming()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)
    val shouldShowButtonPlay get() = _shouldShowButtonPlay

    private val _screenTypes = combine(
        _anime,
        _sourceFragmentId
    ) { anime, sourceFragmentId ->
        if (anime == null) return@combine emptyList()
        if (anime.getId().isNullOrEmpty()) return@combine emptyList()
        buildList {
            if (anime.isTvType() && anime.hasBroadcast()) {
                add(
                    TabLayoutScreenType(
                        title = context.getString(R.string.episode),
                        fragment = AnimeVideosEpisodesFragment.newInstance(
                            AnimeVideosEpisodesArgument(id = anime.getId())
                        )
                    )
                )

                add(
                    TabLayoutScreenType(
                        title = context.getString(R.string.broadcast),
                        fragment = AnimeEpisodesFragment.newInstance(
                            AnimeEpisodesArgument(
                                id = anime.getId(),
                                thumbnail = anime.displayThumbnail()
                            )
                        )
                    )
                )
            }

            add(
                TabLayoutScreenType(
                    title = context.getString(R.string.info),
                    fragment = AnimeMoreInfoFragment.newInstance(
                        AnimeMoreInfoArgument(anime as Anime)
                    )
                )
            )

            add(
                TabLayoutScreenType(
                    title = context.getString(R.string.character),
                    fragment = AnimeCharactersFragment.newInstance(
                        AnimeCharactersArgument(anime.getId())
                    )
                )
            )

            add(
                TabLayoutScreenType(
                    title = context.getString(R.string.picture),
                    fragment = AnimePicturesFragment.newInstance(
                        AnimePicturesArgument(
                            id = anime.getId(),
                            trailer = anime.trailer
                        )
                    )
                )
            )

            add(
                TabLayoutScreenType(
                    title = context.getString(R.string.recommend),
                    fragment = AnimeRecommendationsFragment.newInstance(
                        AnimeRecommendationsArgument(
                            id = anime.getId(),
                            sourceFragmentId = sourceFragmentId
                        )
                    )
                )
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val screenTypes get() = _screenTypes

    private val _fragments = _screenTypes.map {
        it.map { item -> item.fragment }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val fragments get() = _fragments

    private val _shouldShowFullInfo = _screenTypes.map {
        it.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)
    val shouldShowFullInfo get() = _shouldShowFullInfo

    fun setSourceFragmentId(id: Int?) {
        _sourceFragmentId.value = id ?: R.id.homeFragment
    }

    fun fetchAnimeInfo(id: String) {
        handleData(getAnimeFullByIdUseCase.invoke(AnimeParams(id = id))) {
            _anime.emit(it)
        }
    }
}