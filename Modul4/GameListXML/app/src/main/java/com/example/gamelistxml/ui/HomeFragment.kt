package com.example.gamelistxml.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamelistxml.MainViewModel
import com.example.gamelistxml.R
import com.example.gamelistxml.adapter.GameListAdapter
import com.example.gamelistxml.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import com.example.gamelistcomp.MainViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
            requireActivity().application,
            getString(R.string.header_titleEn)
        )
    }
    private lateinit var adapter: GameListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GameListAdapter(
            games = viewModel.gameList.value,
            isIndonesian = viewModel.isIndonesian.value,
            onDetailClick = { gameId ->
                viewModel.onDetailClick(gameId)
            },
            onBrowserClick = { url ->
                viewModel.onBrowserClick(url)
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().finish()
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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isIndonesian.collectLatest { isIndonesian ->
                        adapter.updateLanguage(isIndonesian)
                    }
                }

                launch {
                    viewModel.navigateToDetail.collectLatest { gameId ->
                        if (gameId != null) {
                            val bundle = Bundle().apply { putInt("gameId", gameId) }
                            findNavController().navigate(R.id.action_home_to_detail, bundle)
                            viewModel.onDetailNavigated()
                        }
                    }
                }

                launch {
                    viewModel.openBrwser.collectLatest { url ->
                        if (url != null) {
                            viewModel.onBrowserOpened()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}