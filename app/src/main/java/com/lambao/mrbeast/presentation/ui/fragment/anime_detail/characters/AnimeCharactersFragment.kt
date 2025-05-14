package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.characters

import android.os.Bundle
import com.lambao.base.extension.getParcelableCompat
import com.lambao.base.extension.observeLatest
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimeCharactersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeCharactersFragment :
    BaseVMFragment<FragmentAnimeCharactersBinding, AnimeCharactersViewModel>() {

    companion object {
        fun newInstance(data: AnimeCharactersArgument?): AnimeCharactersFragment {
            val args = Bundle().apply {
                putParcelable(Constants.Bundle.ARG, data)
            }

            val fragment = AnimeCharactersFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val argData by lazy {
        arguments?.getParcelableCompat<AnimeCharactersArgument>(Constants.Bundle.ARG)
    }

    private val charactersAdapter by lazy { AnimeCharactersAdapter() }

    override fun getLayoutResId() = R.layout.fragment_anime_characters

    override fun getViewModelClass() = AnimeCharactersViewModel::class.java

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.rvCharacters.adapter = charactersAdapter
        binding.rvCharacters.spacing {
            start = 8
            end = 8
            top = 64
            bottom = 64
        }
    }

    override fun initObserve() {
        binding.viewModel = viewModel

        observeLatest(viewModel.characters) {
            charactersAdapter.submitList(it)
        }

        argData?.id?.let { viewModel.fetchAnimeCharacters(it) }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}