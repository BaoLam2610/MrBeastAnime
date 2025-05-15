package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.characters

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.domain.model.display.DisplayAnimeCharacterInfo
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeCharactersUseCase
import com.lambao.base.presentation.ui.viewmodel.paging.BaseClientPagingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AnimeCharactersViewModel @Inject constructor(
    private val getAnimeCharactersUseCase: GetAnimeCharactersUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseClientPagingViewModel<DisplayAnimeCharacterInfo>(dispatcherProvider) {

    private val _animeId = MutableStateFlow("")

    private fun setAnimeId(id: String) {
        _animeId.value = id
    }

    override fun fetchData() {
        handleDataNoLoading(getAnimeCharactersUseCase.invoke(AnimeParams(id = _animeId.value))) {
            setFullItemList(it)
        }
    }

    fun fetchAnimeCharacters(id: String) {
        setAnimeId(id)
        resetPaging()
        fetchData()
    }
}