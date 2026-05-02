package com.example.gamelistxml.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gamelistxml.MainViewModel
import com.example.gamelistxml.R
import com.example.gamelistxml.databinding.FragmentDetailBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gameId = arguments?.getInt("gameId")
        val game = viewModel.game.find { it.id == gameId } ?: return

        binding.imageDetail.setImageResource(game.detailImageRes)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_settings -> {
                    findNavController().navigate(R.id.action_global_settingsFragment)
                    true
                }
                else -> false
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isIndonesian.collectLatest { isIndonesian ->
                binding.textTitle.text = getString(game.title)
                binding.textRating.text = getString(game.ratings)
                binding.textReview.text = getString(game.review)
                binding.textDescLabel.text = getString(
                    if (isIndonesian) R.string.desc_textId else R.string.desc_textEn
                )
                binding.textDescription.text = getString(
                    if (isIndonesian) game.descriptionId else game.descriptionEn
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}