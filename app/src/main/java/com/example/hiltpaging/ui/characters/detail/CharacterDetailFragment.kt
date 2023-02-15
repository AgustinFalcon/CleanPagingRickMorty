package com.example.hiltpaging.ui.characters.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.hiltpaging.databinding.FragmentCharacterDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterDetailBinding.inflate(layoutInflater, container, false)

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        onBindData()

        return binding.root
    }

    private fun onBindData() {
        with(binding) {
            character = args.character
            ViewCompat.setTransitionName(binding.ivAvatar, "avatar_${args.character.id}")
            ViewCompat.setTransitionName(binding.tvName, "name_${args.character.id}")
            ViewCompat.setTransitionName(binding.tvStatus, "status_${args.character.id}")
        }
    }


}