package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.characters

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.model.anime.AnimeCharacter
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AnimeCharactersViewModel @Inject constructor(
    private val getAnimeCharactersUseCase: GetAnimeCharactersUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _characters = MutableStateFlow<List<AnimeCharacter>>(emptyList())
    val characters = _characters.asStateFlow()

    fun fetchAnimeCharacters(id: String) {
        handleDataNoLoading(getAnimeCharactersUseCase.invoke(AnimeParams(id = id))) {
            _characters.emit(it.take(20))
        }
    }
}