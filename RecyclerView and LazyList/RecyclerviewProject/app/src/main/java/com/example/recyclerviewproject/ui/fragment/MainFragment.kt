package com.example.recyclerviewproject.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewproject.databinding.FragmentMainBinding
import com.example.recyclerviewproject.ui.MainViewModel
import com.example.recyclerviewproject.ui.fragment.adapter.ItemCardAdapter
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ItemCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = ItemCardAdapter(
            onItemClicked = { item ->
                Toast.makeText(
                    requireContext(),
                    "Item telah ditekan untuk item ${item.id}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onSwitchToggled = { item, isChecked ->
                viewModel.updateSwitchState(item.id, isChecked)

                val message = if (isChecked) "Switch hidup pada item ${item.id}" else "Switch mati pada item ${item.id}"
                Toast.makeText(
                    requireContext(),
                    message,
                    Toast.LENGTH_SHORT
                ).show()
            },
            onButtonClicked = { item ->
                Toast.makeText(
                    requireContext(),
                    "Tombol telah ditekan untuk item ${item.id}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    adapter.submitList(uiState.itemList)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}