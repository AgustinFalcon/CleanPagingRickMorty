package com.example.hiltpaging.ui.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.hiltpaging.data.model.Character
import com.example.hiltpaging.databinding.FragmentCharactersBinding
import com.example.hiltpaging.databinding.ItemCharacterBinding
import com.example.hiltpaging.ui.adapters.CharacterAdapter
import com.example.hiltpaging.ui.adapters.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class CharactersFragment : Fragment(), CharacterAdapter.CharacterClickListener {


    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private val charactersViewModel: CharactersViewModel by viewModels()

    @Inject
    lateinit var characterAdapter: CharacterAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCharactersBinding.inflate(layoutInflater, container, false)

        with(binding) {
            with(characterAdapter) {
                rvCharacters.apply {
                    postponeEnterTransition()
                    viewTreeObserver.addOnPreDrawListener {
                        startPostponedEnterTransition()
                        true
                    }
                }
                rvCharacters.adapter = withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(this),
                    footer = PagingLoadStateAdapter(this)
                )

                swipeRefresh.setOnRefreshListener { refresh() }
                characterClickListener = this@CharactersFragment

                with(charactersViewModel) {
                    launchOnLifecycleScope {
                        charactersFlow.collectLatest { submitData(it) }
                    }
                    launchOnLifecycleScope {
                        loadStateFlow.collectLatest {
                            swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
                        }
                    }
                }
            }
        }


        return binding.root
    }


    fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            execute()
        }
    }

    override fun onCharacterClicked(binding: ItemCharacterBinding, character: Character) {
        val extras = FragmentNavigatorExtras(
            binding.ivAvatar to "avatar_${character.id}",
            binding.tvName to "name_${character.id}",
            binding.tvStatus to "status_${character.id}"
        )
        findNavController().navigate(
            CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(character),
            extras
        )
    }

}